package com.wyz.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StreamUtils;


/**
 * Created by Jacky on 2016/4/7.
 */
public final class MultiReadHttpServletRequest extends HttpServletRequestWrapper
{

	private byte[] bytes;

	public MultiReadHttpServletRequest(HttpServletRequest request)
			throws IOException
	{
		super(request);
		bytes = StreamUtils.copyToByteArray(request.getInputStream());
	}
	

	@Override
	public ServletInputStream getInputStream() throws IOException
	{
		return new ServletInputStream()
		{
			private final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

			@Override
			public int read() throws IOException
			{
				return byteArrayInputStream.read();
			}

		};
	}

}

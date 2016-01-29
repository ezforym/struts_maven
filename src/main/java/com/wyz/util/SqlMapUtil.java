package com.wyz.util;

import java.io.IOException;
import java.io.Reader;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlMapUtil {
	private static SqlMapClient sqlmap;

	static {
		String resource = "com/wyz/config/SqlMapConfig.xml";
		try {
			Reader reader = Resources.getResourceAsReader(resource);
			sqlmap = SqlMapClientBuilder.buildSqlMapClient(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SqlMapClient getInstance() {
		return sqlmap;
	}

}

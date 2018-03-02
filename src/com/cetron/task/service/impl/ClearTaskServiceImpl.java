package com.cetron.task.service.impl;

import com.cetron.api.cache.TokenCache;
import com.cetron.task.service.ClearTaskService;

public class ClearTaskServiceImpl implements ClearTaskService {

	@Override
	public void clearToken() {
		TokenCache.clear();
	}
}

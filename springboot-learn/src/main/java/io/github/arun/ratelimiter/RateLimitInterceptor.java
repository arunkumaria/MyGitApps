package io.github.arun.ratelimiter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

	private static final int MAX_REQUESTS = 5;
	private static final long TIME_WINDOW = 60_000; // 1 minute

	private final ConcurrentHashMap<String, RequestInfo> requestMap = new ConcurrentHashMap<>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String ip = request.getRemoteAddr();
		long currentTime = System.currentTimeMillis();

		requestMap.putIfAbsent(ip, new RequestInfo(0, currentTime));
		RequestInfo info = requestMap.get(ip);

		if (currentTime - info.startTime > TIME_WINDOW) {
			info.count = 1;
			info.startTime = currentTime;
		} else {
			info.count++;
		}

		if (info.count > MAX_REQUESTS) {
			response.setStatus(429);
			response.getWriter().write("Too many requests. Try again later.");
			return false;
		}

		return true;
	}

	static class RequestInfo {
		int count;
		long startTime;

		RequestInfo(int count, long startTime) {
			this.count = count;
			this.startTime = startTime;
		}
	}
}



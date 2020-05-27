package com.test.cache.test;

import com.test.cache.MemoryCache;

public class MemoryCacheTest {

	public static void main(String[] args) throws InterruptedException {

		MemoryCacheTest memoryCache = new MemoryCacheTest();

		System.out.println("\n\n==========Test1: AddRemoveObjects ==========");
		memoryCache.memoryTestAddRemoveObjects();
		System.out.println("\n\n==========Test2: TestExpiredCacheObjects ==========");
		memoryCache.memoryTestExpiredCacheObjects();
		System.out.println("\n\n==========Test3: TestObjectsCleanupTime ==========");
		memoryCache.memoryTestObjectsCleanupTime();
	}

	private void memoryTestAddRemoveObjects() {

		MemoryCache<String, String> cache = new MemoryCache<String, String>(200, 500, 6);

		cache.put("DBS", "DBS");
		cache.put("UOB", "UOB");
		cache.put("OCBC", "OCBC");
		cache.put("MayBank", "MayBank");
		cache.put("ICICI", "ICICI");
		cache.put("SBI", "SBI");

		System.out.println("6 Cache Object Added.. cache.size(): " + cache.size());
		cache.remove("MayBank");
		System.out.println("One object removed.. cache.size(): " + cache.size());

		cache.put("HDFC", "HDFC");
		cache.put("Amex", "Amex");
		System.out.println("Two objects Added but reached maxItems.. cache.size(): " + cache.size());

	}

	private void memoryTestExpiredCacheObjects() throws InterruptedException {

		// Test with memoryTimeToLive = 2 second
		// memoryTimerInterval = 2 second
		// maxItems = 10
		MemoryCache<String, String> cache = new MemoryCache<String, String>(2, 2, 10);

		cache.put("DBS", "DBS");
		cache.put("UOB", "UOB");
		Thread.sleep(1000);

		System.out.println("Two objects are added but reached timeToLive. cache.size(): " + cache.size());

	}

	private void memoryTestObjectsCleanupTime() throws InterruptedException {
		int size = 500000;

		// Test with timeToLiveInSeconds = 10 seconds
		// timerIntervalInSeconds = 10 seconds
		// maxItems = 50000

		MemoryCache<String, String> cache = new MemoryCache<String, String>(10, 10, 50000);

		for (int i = 0; i < size; i++) {
			String value = Integer.toString(i);
			cache.put(value, value);
		}

		Thread.sleep(100);

		long start = System.currentTimeMillis();
		cache.cleanup();
		double finish = (double) (System.currentTimeMillis() - start) / 1000.0;

		System.out.println("Cleanup times for " + size + " objects are " + finish + " s");

	}
}
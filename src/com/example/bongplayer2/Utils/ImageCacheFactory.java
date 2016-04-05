package com.example.bongplayer2.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.bongplayer2.interfaces.ImageCache;

public class ImageCacheFactory {

	private static ImageCacheFactory instance = new ImageCacheFactory();

	public static ImageCacheFactory getInstance() {
		return instance;
	}

	private HashMap<String, ImageCache> cacheMap = new HashMap<String, ImageCache>();

	private ImageCacheFactory() {
	}

	public ImageCache createMemoryCache(String cacheName, int imageMaxCounts) {
		synchronized (cacheMap) {
			checkAleadyExists(cacheName);
			ImageCache cache = new MemoryImageCache(imageMaxCounts);
			cacheMap.put(cacheName, cache);
			return cache;
		}
	}

	private void checkAleadyExists(String cacheName) {
		ImageCache cache = cacheMap.get(cacheName);
		if (cache != null) {
//			throw new ImageCacheAleadyExistException(String.format(
//					"ImageCache[%s] aleady exists", cacheName));
		}
	}

	public ImageCache createTwoLevelCache(String cacheName, int imageMaxCounts) {
		synchronized (cacheMap) {
			checkAleadyExists(cacheName);
			List<ImageCache> chain = new ArrayList<ImageCache>();
			chain.add(new MemoryImageCache(imageMaxCounts));
			//chain.add(new FileImageCache(cacheName));
			ChainedImageCache cache = new ChainedImageCache(chain);
			cacheMap.put(cacheName, cache);
			return cache;
		}
	}

	public ImageCache get(String cacheName) {
		ImageCache cache = cacheMap.get(cacheName);
		if (cache == null) {
//			throw new ImageCacheNotFoundException(
//					String.format("ImageCache[%s] not founds"));
		}
		return cache;
	}
}

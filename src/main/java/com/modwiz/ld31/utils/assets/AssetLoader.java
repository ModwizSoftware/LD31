package com.modwiz.ld31.utils.assets;

import com.modwiz.ld31.utils.annotations.NotNull;
import com.modwiz.ld31.utils.annotations.Nullable;
import com.modwiz.ld31.utils.assets.loaders.BufferedImageLoader;
import com.modwiz.ld31.utils.assets.loaders.ILoader;

import java.io.File;
import java.util.HashMap;

public class AssetLoader {
    private HashMap<Class, ILoader> loaderMap = new HashMap<>();
    private ProviderBase streamProvider;

    public AssetLoader(File rootDir) {
        streamProvider = new ProviderBase(rootDir);
        registerLoader(new BufferedImageLoader());
    }

    public boolean registerLoader(@NotNull ILoader assetLoader) {
        if (assetLoader == null) {
            return false;
        }

        if (loaderMap.containsKey(assetLoader.getClass())) {
            return false;
        }

        loaderMap.put(assetLoader.getClass(), assetLoader);
        return true;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T loadAsset(Class<T> type, String path) {
        if (!loaderMap.containsKey(type)) {
            return null;
        }

        return (T) loaderMap.get(type).getContent(streamProvider.provideAsset(path), path);
    }

}

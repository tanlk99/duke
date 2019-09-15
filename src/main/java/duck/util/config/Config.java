package duck.util.config;

class Config {
    private static final String DEFAULT_CACHE_PATH = "data/duck-cache.txt";
    private static final String DEFAULT_ARCHIVE_PATH = "archive/duck-archive.txt";

    private String cachePath;
    private String archivePath;

    /**
     * Returns a Config object containing Duck's default configuration.
     *
     * @return Config object with default configuration
     */
    static Config getDefaultConfig() {
        Config defaultConfig = new Config();
        defaultConfig.setCachePath(DEFAULT_CACHE_PATH);
        defaultConfig.setArchivePath(DEFAULT_ARCHIVE_PATH);
        return defaultConfig;
    }

    /**
     * Returns the configured cache path.
     *
     * @return Relative path to cache file
     */
    String getCachePath() {
        return cachePath;
    }

    /**
     * Returns the configured archive path.
     *
     * @return Relative path to archive file
     */
    String getArchivePath() {
        return archivePath;
    }

    /**
     * Sets the configured cache path.
     *
     * @param cachePath Relative path to cache file
     */
    void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }

    /**
     * Sets the configured archive path.
     *
     * @param archivePath Relative path to archive file
     */
    void setArchivePath(String archivePath) {
        this.archivePath = archivePath;
    }
}

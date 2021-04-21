package framework.configuration;

import framework.utils.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static java.lang.String.format;

/**
 * Класс-утилита для работы с *.properties файлами.
 */
public final class PropertiesResourceManager {

    private static final Logger LOG = Logger.getInstance();
    private Properties properties;

    /**
     * Инициализация свойств значениями из ресурсного файла *.properties.
     *
     * @param resourceName имя ресурсного файла.
     */
    public PropertiesResourceManager(final String resourceName) {
        properties = new Properties();
        appendFromResource(properties, resourceName);
    }

    /**
     * Инициализация свойств значениями из ресурсных файлов *.properties. Основного и дополнительного.     *
     *
     * @param defaultResourceName имя дефолтного ресурсного файла.
     * @param resourceName        имя дополнительного ресурсного файла.
     */
    public PropertiesResourceManager(final String defaultResourceName, final String resourceName) {
        this(defaultResourceName);
        appendFromResource(properties, resourceName);
    }

    /**
     * Добавление к текущему набору свойств, свойств из нового ресурсного файла.
     *
     * @param properties   текущий набор свойств.
     * @param resourceName имя ресурсвного *.properties файла, значения которого необходимо добавить к текущему набору.
     */
    private void appendFromResource(final Properties properties, final String resourceName) {
        try (InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (inStream != null) {
                properties.load(new InputStreamReader(inStream, StandardCharsets.UTF_8));
            } else {
                LOG.error(format("Resource \"%1$s\" could not be found", resourceName));
            }
        } catch (IOException e) {
            LOG.fatal("Error occurred during appending resource file", e);
        }
    }

    /**
     * Получение значения по ключу. Поиск среди системых свойств, и в случае неудачи получение значения из файла.
     *
     * @param key ключ (имя свойства), по которому необходимо искать значение.
     * @return значение системного свойства, если найдено. Иначе значение из properties файла.
     */
    public String getProperty(final String key) {
        return System.getProperty(key, properties.getProperty(key));
    }

    /**
     * Получение значения по ключу. Поиск среди системых свойств, и в случае неудачи получение значения из файла.
     * Если значения нет ни среди системных свойств, ни среди значений properties файла, то возвращается дефолтное
     * значение, переданное отдельным параметром.
     *
     * @param key          ключ (имя свойства), по которому необходимо искать значение.
     * @param defaultValue дефолтное значение, которое возвращается в случае, если свойство не найдено.
     * @return значение системного свойства или значение из properties файла, если найдено. Иначе дефолтное значение.
     */
    public String getProperty(final String key, final String defaultValue) {
        return System.getProperty(key, properties.getProperty(key, defaultValue));
    }

    /**
     * Добавление новой пары ключ-значение в свойство.
     *
     * @param key   ключ (имя свойства).
     * @param value значение свойства.
     */
    public void setProperty(final String key, final String value) {
        properties.setProperty(key, value);
    }
}


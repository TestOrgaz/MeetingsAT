package framework.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;

import static java.lang.String.format;

/**
 * Класс-утилита для работы с файлами.
 */
public class FileManager {

    private static final Logger LOG = Logger.getInstance();

    private FileManager() {
        throw new IllegalStateException("File manager class. All methods are static, don't create instance of this class");
    }

    /**
     * Копирование файла.
     *
     * @param source      исходный файл для копирования.
     * @param destination путь к новой копии файла.
     * @return true - если файл был скопирован, иначе - false.
     */
    public static boolean copyFile(String source, String destination) {
        if (!fileExists(source)) {
            LOG.warn(format("File %s not found", source));
            return false;
        }
        try {
            Files.copy(Paths.get(source), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException exception) {
            LOG.fatal(format("File %s not copied", source), exception);
            return false;
        }
    }

    /**
     * Удаление всех файлов в директории и самого директория.
     *
     * @param directoryPath путь к директорию с файлами.
     * @return true - в случае успешного выполнения, иначе - false.
     */
    public static boolean deleteFolderWithFiles(String directoryPath) {
        LOG.info(format("Delete folder with files: %s", directoryPath));
        return deleteAllFilesInFolder(directoryPath) && deleteFile(directoryPath);
    }

    /**
     * Удаление всех файлов в директории и самого директория.
     *
     * @param directoryPath путь к директорию с файлами.
     */
    public static void deleteAllSubdirectiories(String directoryPath) {
        LOG.info(format("Delete folder with files: %s", directoryPath));
        File[] listOfFiles = new File(directoryPath).listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                try {
                    FileUtils.deleteDirectory(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Рекурсивное удалении всех файлов в директории.
     *
     * @param directoryPath путь к директорию с файлами.
     * @return true - в случае успешного выполнения, иначе - false.
     */
    public static boolean deleteAllFilesInFolder(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File myFile : files) {
                    deleteFile(myFile.getAbsolutePath());
                }
            }
        } else {
            LOG.warn(format("Folder not found: %s", directoryPath));
            return false;
        }
        return true;
    }

    /**
     * Удаление файла.
     *
     * @param path путь к файлу, который необходимо удалить.
     * @return true - в случае успешного выполнения, иначе - false.
     */
    public static boolean deleteFile(String path) {
        try {
            Files.delete(Paths.get(path));
            LOG.info(format("Deleted file: %s", path));
            return true;
        } catch (IOException e) {
            LOG.fatal("Error occurred during file deletion", e);
            return false;
        }
    }

    /**
     * Создание нового директория, если такого нет.
     *
     * @param path путь к директорию.
     * @return true - в случае успешного выполнения, иначе - false.
     */
    public static boolean createDirectory(String path) {
        LOG.info(format("Create directory: %s", path));
        if (fileExists(path)) {
            LOG.info(format("The following directory already exists: %s", path));
            return false;
        } else {
            return new File(path).mkdirs();
        }
    }

    /**
     * Копирование файлов одной директории в другую директорию
     *
     * @param source      Путь откуда копируем.
     * @param destination Путь куда копируем.
     */
    public static void copyDirectoryWithSubdirectories(String source, String destination) {
        File srcDir = new File(source);
        File destDir = new File(destination);

        try {
            FileUtils.copyDirectoryToDirectory(srcDir, destDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получение последнего обновленного файла с определенным расширением в директории.
     *
     * @param directoryPath путь к директорию с файлами.
     * @param fileExtension расширение файла.
     * @return последний модифицированный файл.
     */
    public static String getLastModifiedFilePath(String directoryPath, String fileExtension) {
        LOG.info(format("Get last modified file in folder: %s", directoryPath));
        File lastModifiedFile = null;

        File[] files = new File(directoryPath).listFiles();
        if (files != null) {
            for (File myFile : files) {
                String modifiedFileExtension = getFileExtension(myFile);
                if (lastModifiedFile == null && modifiedFileExtension.equals(fileExtension)) {
                    lastModifiedFile = myFile;
                    continue;
                }

                if (lastModifiedFile != null) {
                    Date lastModifiedFileTime = new Date(lastModifiedFile.lastModified());
                    Date myFileTime = new Date(myFile.lastModified());
                    if (lastModifiedFileTime.before(myFileTime) && modifiedFileExtension.equals(fileExtension)) {
                        lastModifiedFile = myFile;
                    }
                }
            }
        }
        return lastModifiedFile != null ? lastModifiedFile.getAbsolutePath() : null;
    }

    /**
     * Проверка существует ли файл по указанному пути.
     *
     * @param filePath путь к файлу.
     * @return true - файл найден, иначе - false.
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * Проверка есть ли в директории файл с указанным расширением.
     *
     * @param directoryPath путь к директорию с файлами.
     * @param extension     расширение файла для поиска.
     * @return true - файл найден, иначе - false.
     */
    public static boolean isFileWithExtensionExist(String directoryPath, String extension) {
        File[] files = new File(directoryPath).listFiles();
        return files != null && Arrays.stream(files).anyMatch(file -> getFileExtension(file).equals(extension));
    }

    /**
     * Получение имени файла без расширения.
     *
     * @param fileWithExt полное имя файла.
     * @return имя файла без расширения.
     */
    public static String getFileName(String fileWithExt) {
        return fileWithExt.split("\\.")[0];
    }

    /**
     * Получение расширения файла.
     *
     * @param filePath полной путь к файлу.
     * @return расширение файла.
     */
    public static String getFileExtension(String filePath) {
        return getFileExtension(new File(filePath));
    }

    /**
     * Получение расширения файла.
     *
     * @param file файл.
     * @return расширение файла.
     */
    public static String getFileExtension(File file) {
        return FilenameUtils.getExtension(file.getName());
    }

    /**
     * Проверка пустой ли директорий.
     *
     * @param dirPath путь к директорию.
     * @return true - директорий пустой, иначе - false.
     */
    public static boolean isDirectoryEmpty(String dirPath) {
        return getCountOfFilesInFolder(dirPath) == 0;
    }

    /**
     * Получение количества файлов в директории.
     *
     * @param dirPath путь к директорию.
     * @return количество файлов в директории.
     */
    public static int getCountOfFilesInFolder(String dirPath) {
        File[] files = new File(dirPath).listFiles();
        return files != null ? files.length : 0;
    }

    /**
     * Чтение данных из файла в строку. Если при чтении возникла ошибка, возвращается null.
     *
     * @param filename имя файла (путь).
     * @return данный из файла, преобразованные в строкую.
     */
    public static String readFileToString(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            LOG.error(format("Error occurred during file reading: %s", filename));
            return null;
        }
    }

    /**
     * Получение абсолютного пути к файлу.
     *
     * @param path путь к файлу.
     * @return абсолютный путь к файлу.
     */
    public static String getAbsolutePath(Path path) {
        return getAbsolutePath(path.toString());
    }

    /**
     * Получение абсолютного пути к файлу. Поиск производится относительно рабочего директория, либо проектного.
     *
     * @param path путь к файлу.
     * @return абсолютный путь к файлу.
     */
    public static String getAbsolutePath(String path) {
        String userDir = System.getProperty("user.dir");
        String baseDir = userDir != null ? userDir : System.getProperty("project.basedir");
        return Paths.get(baseDir, path).toString();
    }
}

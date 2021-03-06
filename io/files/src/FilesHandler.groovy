import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class FilesHandler {


    /**
     * Locate files based on Ant-style patterns
     *
     * @param pwd Parent working directory
     * @param glob The Ant-style pattern to select files
     * @param exclude the Ant-style pattern to exclude files
     * @return List of String with file's absolute path
     */
    List<String> glob(File pwd, String glob = "*", String exclude = "") {
        assert pwd, 'Pwd (print working directory) is required.'
        assert glob, 'Glob is required'

        return this.glob(pwd.absolutePath, glob, exclude)
    }

    /**
     * Locate files based on Ant-style patterns
     *
     * @param pwd Parent working directory
     * @param glob The Ant-style pattern to select files
     * @param exclude the Ant-style pattern to exclude files
     * @return List of String with file's absolute path
     */
    List<String> glob(String pwd, String glob = "*", String exclude = "") {
        assert pwd, 'Parent working directory is required.'
        assert glob, 'Glob is required. Can be empty'
        assert glob, 'Glob is required. Can be empty'

        def regexGlob = glob
                .replace("\\", "/")
                .replace("/", "\\/")
                .replace(".", "\\.")
                .replace("*", ".*")
                .replace("?", ".*")

        def regexExclude = exclude

        if (regexExclude)
            regexExclude = regexExclude
                .replace("\\", "/")
                .replace("/", "\\/")
                .replace(".", "\\.")
                .replace("*", ".*")
                .replace("?", ".*")

        return Files.walk(Paths.get(pwd))
                .parallel()
                .filter  { Path p -> Files.isRegularFile(p) }
                .collect { Path p -> p.toFile() }
                .findAll { File f -> !exclude || !f.absolutePath.matches(regexExclude) }
                .findAll { File f -> !glob || f.absolutePath.matches(regexGlob) }
                .collect { File f -> f.absolutePath }
    }

    /**
     * Locate files on pure Java framework
     * @param pwd parent working directory
     * @param pattern the pattern to select files
     * @param exclude the pattern to exclude files
     * @return List of File object.
     */
    List<File> find(String pwd, String pattern = "", String exclude = "") {
        assert pwd, 'Pwd (print working directory) is required.'

        return Files.walk(Paths.get(pwd))
                .parallel()
                .filter({ Path p -> Files.isRegularFile(p) })
                .collect{ Path p -> p.toFile() }
                .findAll { File f -> !exclude || !f.absolutePath.contains(exclude) }
                .findAll { File f -> !pattern || f.absolutePath.contains(pattern) }
    }
}
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParseCSV {

    public static ServerSettings parseCSV(String fileName) {
        Logger logger = Log.getInstance();
        logger.log("запущен парсинг файла "+ fileName);
        File file = new File(fileName);
        List<ServerSettings> result = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(file)).build()) {
            result = reader.readAll().stream().map(data->
                new ServerSettings(data[0],Integer.parseInt(data[1])))
                           .collect(Collectors.toList());

        } catch (Exception exception) {
            logger.log(exception.getMessage());
            exception.printStackTrace();
        }
        logger.log("файл прочитан");
        return result.get(0);
    }
}

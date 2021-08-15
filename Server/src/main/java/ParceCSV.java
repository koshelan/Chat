import com.opencsv.CSVReader;
import com.opencsv.bean.*;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ParceCSV {
    public static SeverSettings parceCSV(String[] columnMapping,String fileName){
        File file = new File(fileName);
        ArrayList<SeverSettings> result = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            ColumnPositionMappingStrategy<SeverSettings> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(SeverSettings.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<SeverSettings> csv = new CsvToBeanBuilder<SeverSettings>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            result = csv.parse();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result.get(0);
    }
}

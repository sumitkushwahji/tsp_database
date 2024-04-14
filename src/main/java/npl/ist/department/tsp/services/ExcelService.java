package npl.ist.department.tsp.services;

import npl.ist.department.tsp.models.Item;
import npl.ist.department.tsp.repositories.ItemRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private ItemRepository itemRepository;

//    public List<Item> readExcel(MultipartFile file) throws IOException {
//        List<Item> items = new ArrayList<>();
//        Workbook workbook = new XSSFWorkbook(file.getInputStream());
//        Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
//
//        Iterator<Row> iterator = sheet.iterator();
//        while (iterator.hasNext()) {
//            Row currentRow = iterator.next();
//            if (currentRow.getRowNum() == 0) { // Skip header row
//                continue;
//            }
//            Item item = mapRowToItem(currentRow);
//            items.add(item);
//        }
//        workbook.close();
//
//        // Save all items to the database
//        return itemRepository.saveAll(items);
//    }
public List<Item> readExcel(MultipartFile file) throws IOException {
    List<Item> items = new ArrayList<>();
    Workbook workbook = new XSSFWorkbook(file.getInputStream());
    Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

    Iterator<Row> iterator = sheet.iterator();
    while (iterator.hasNext()) {
        Row currentRow = iterator.next();
        if (currentRow.getRowNum() == 0) { // Skip header row
            continue;
        }
        Item item = mapRowToItem(currentRow);
        // Check if item with same details already exists in database
        if (!itemExists(item)) {
            items.add(item);
        }
    }
    workbook.close();
    // Save all new items to the database
    return itemRepository.saveAll(items);
}

    private boolean itemExists(Item item) {
        // Check if an item with the same details exists in the database
        return itemRepository.existsByItemNameAndIndentorAndEmployeeIdAndTotalQty(
                item.getItemName(), item.getIndentor(), item.getEmployeeId(), item.getTotalQty());
    }
    private Item mapRowToItem(Row row) {
        Item item = new Item();
        item.setItemName(getStringCellValue(row.getCell(1)));
        item.setEmployeeId(getNumericCellValue(row.getCell(2)));
        item.setIndentor(getStringCellValue(row.getCell(3)));
        item.setTotalQty(getNumericCellValueAsInt(row.getCell(4))); // Adjusted here
        item.setTenderNo(getStringCellValue(row.getCell(5)));
        item.setPurchaseOrderDate(getDateCellValue(row.getCell(6)));
        item.setPurchaseOrderNo(getStringCellValue(row.getCell(7)));
        item.setDateOfDeliveryISTRAC(getDateCellValue(row.getCell(8)));
        item.setQuantityISTRAC(getNumericCellValueAsInt(row.getCell(9))); // Adjusted here
        item.setInstallationDateISTRAC(getDateCellValue(row.getCell(10)));
        item.setDateOfDeliveryNPL(getDateCellValue(row.getCell(11)));
        item.setQuantityNPL(getNumericCellValueAsInt(row.getCell(12))); // Adjusted here
        item.setInstallationDateNPL(getDateCellValue(row.getCell(13)));
        item.setWhiteSlipDate(getDateCellValue(row.getCell(14)));
        item.setWhiteSlipNo(getStringCellValue(row.getCell(15)));
        item.setWhiteSlipGivenBy(getStringCellValue(row.getCell(16)));
        item.setRemarks(getStringCellValue(row.getCell(17)));
        item.setCategory(getStringCellValue(row.getCell(18)));
        return item;
    }





    private int getNumericCellValueAsInt(Cell cell) {
        if (cell == null) {
            return 0; // or throw an exception based on your requirement
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) Math.round(cell.getNumericCellValue());
        } else {
            return 0; // or handle the case where the cell contains a non-numeric value
        }
    }

    private long getNumericCellValueAsLong(Cell cell) {
        if (cell == null) {
            return 0L; // or throw an exception based on your requirement
        }
        return (long) cell.getNumericCellValue();
    }


    private Date getDateCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            return cell.getDateCellValue();
        } catch (IllegalStateException e) {
            // Handle the case when the cell contains a date formatted as a string
            try {
                return new SimpleDateFormat("MM/dd/yyyy").parse(cell.getStringCellValue());
            } catch (ParseException ex) {
                // Log or handle the parse exception as needed
                return null;
            }
        }
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            // Convert numeric value to string
            return String.valueOf(cell.getNumericCellValue());
        } else {
            // Handle other cell types if needed
            return null;
        }
    }

    private Long getNumericCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            // Convert numeric value to long
            return (long) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            // Try to parse the string value as a long
            try {
                return Long.parseLong(cell.getStringCellValue().trim());
            } catch (NumberFormatException e) {
                // Handle parsing exception
                return null;
            }
        } else {
            // Handle other cell types if needed
            return null;
        }
    }


}

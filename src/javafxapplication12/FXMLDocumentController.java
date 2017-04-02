/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication12;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import static java.nio.file.Files.list;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Collections.list;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
/**
 *
 * @author user
 */
public class FXMLDocumentController implements Initializable {
  ////////////////////\///Основная таблица////////////////////////////////////////////////////////  
    @FXML
    private ObservableList<String> res = FXCollections.observableArrayList("PASS", "FAIL");
    
    @FXML
    private ObservableList<Check> data = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Check> checkView;
    
    @FXML
    private TableColumn<Check, Integer> id;
    
    @FXML
    private TableColumn<Check, String> condition;
    
    @FXML
    private TableColumn<Check, String> result;
    
    @FXML
    private TableColumn<Check, String> date;
    
    @FXML
    private TableColumn<Check, String> responsible;
////////////////////////////////////////////////////////
/////////////////////////Файлы с версиями ////////////////////////////////////
    @FXML
    private TableView<Version_file> FileTable;
    
    @FXML
    private ObservableList<Version_file> ver_list = FXCollections.observableArrayList();
    
    @FXML
    private TableColumn<Version_file, String> time_version;
    
    @FXML
    private TableColumn<Version_file, String> date_version;
    
    @FXML
    private TableColumn<Version_file, Integer> number_version;
    
    @FXML
    private Button add;
    
    @FXML
    private Button delete;
    
    @FXML
    private Button save;
    public FXMLDocumentController() {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setCellFactory(TextFieldTableCell.<Check, Integer>forTableColumn(new IntegerStringConverter()));
        condition.setCellValueFactory(new PropertyValueFactory<>("condition"));
        condition.setCellFactory(TextFieldTableCell.<Check>forTableColumn());
        result.setCellValueFactory(new PropertyValueFactory<>("result"));
        result.setCellFactory(ComboBoxTableCell.forTableColumn(res));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        responsible.setCellValueFactory(new PropertyValueFactory<>("responsible"));
        responsible.setCellFactory(TextFieldTableCell.<Check>forTableColumn());
        
        checkView.setItems(data);
        
        
        number_version.setCellValueFactory(new PropertyValueFactory<>("number"));
        number_version.setCellFactory(TextFieldTableCell.<Version_file, Integer>forTableColumn(new IntegerStringConverter()));
        time_version.setCellValueFactory(new PropertyValueFactory<>("time"));
        time_version.setCellFactory(TextFieldTableCell.<Version_file>forTableColumn());
        date_version.setCellValueFactory(new PropertyValueFactory<>("date"));
        date_version.setCellFactory(TextFieldTableCell.<Version_file>forTableColumn());
        
        FileTable.setItems(ver_list);      
    }    
    
    ArrayList File_name = new ArrayList();
    public void Close_file() {
        data.clear();
        add.setVisible(true);
        delete.setVisible(true);
        save.setVisible(true);
    }
    
    public void OpenFile() throws IOException{
        TablePosition pos = FileTable.getSelectionModel().getSelectedCells().get(0);
        int row_file = pos.getRow();
        System.out.println(File_name.get(row_file));
        add.setVisible(false);
        delete.setVisible(false);
        save.setVisible(false);
        
        data.clear();
       
        InputStream in = new FileInputStream("Version/"+(String) File_name.get(row_file));
        HSSFWorkbook wb = new HSSFWorkbook(in);
        Sheet s = wb.getSheetAt(0);
        for(int i=0;i<=s.getPhysicalNumberOfRows()-1;i++)
        {
        String condition = wb.getSheetAt(0).getRow(i).getCell(1).getStringCellValue();
        String result = wb.getSheetAt(0).getRow(i).getCell(2).getStringCellValue();
        String date = wb.getSheetAt(0).getRow(i).getCell(3).getStringCellValue();
        String tester = wb.getSheetAt(0).getRow(i).getCell(4).getStringCellValue();
        
            Check ch = new Check(data.size()+1,condition , result, date , tester);
            data.add(ch);
        }
    }
    
   
    

    public void Upload_files(){
        ver_list.clear();
        File []fList;        
        File F = new File("Version/");
        fList = F.listFiles();     
        for(int i=0; i<fList.length; i++)           
        {
            //Нужны только папки в место isFile() пишим isDirectory()
            if(fList[i].isFile()){              
                Version_file ch = new Version_file(i+1, fList[i].getName().substring(0,8), fList[i].getName().substring(9, 17).replaceAll("-", ":"));
                ver_list.add(ch);
                File_name.add(fList[i].getName());
            }
        }
       
    }
    
    public void add(){
        Check ch = new Check(data.size()+1, "Ваше условие", "PASS", getTodayTime() ,"Кто добавил");
        data.add(ch);
    }
    
    public void change(TableColumn.CellEditEvent t){ 
            int column = t.getTableView().getEditingCell().getColumn(); 
            switch(column){ 
        case 1: 
            ((Check)t.getTableView().getItems().get(t.getTablePosition().getRow())).setCondition(t.getNewValue().toString()); 
            break; 
        case 2: 
            ((Check)t.getTableView().getItems().get(t.getTablePosition().getRow())).setResult(t.getNewValue().toString()); 
            break; 
        case 4: 
            ((Check)t.getTableView().getItems().get(t.getTablePosition().getRow())).setResponsible(t.getNewValue().toString()); 
            break; 
        } 
    }
    
    public void delete(){
        int selectedIndex = checkView.getSelectionModel().getSelectedIndex(); 
        data.remove(selectedIndex);
        
        for(int i=0;i< data.size();i++)
        {
            data.get(i).setId(i+1);
        }
    }
    
    private static SimpleDateFormat d = new SimpleDateFormat("yy-MM-dd");
    private static SimpleDateFormat t = new SimpleDateFormat("HH-mm-ss");
 
    public void save() throws IOException{
       String date = d.format( System.currentTimeMillis() );
       String time = t.format( System.currentTimeMillis() );
       
File file = new File("Version/"+date+" "+time+".xls");
int i = 0; 
ArrayList list = new ArrayList(); 
while (i < data.size()) list.add(data.get(i++)); //Добавление данных в лист 
parse(file, list);  
    }
    
public void parse(File file, ArrayList<Check> list) throws FileNotFoundException, IOException{
Workbook book = new HSSFWorkbook(); 
Sheet sheet = book.createSheet("new Sheet"); 
int i = 0; 
while(i != list.size()){ 
Row row = sheet.createRow(i); 
row.createCell(0).setCellValue(list.get(i).getId()); 
row.createCell(1).setCellValue(list.get(i).getCondition()); 
row.createCell(2).setCellValue(list.get(i).getResult()); 
row.createCell(3).setCellValue(list.get(i).getDate().toString()); 
row.createCell(4).setCellValue(list.get(i).getResponsible()); 
i++; 
} 
sheet.autoSizeColumn(1); 
FileOutputStream out = new FileOutputStream(file); 
book.write(out); 
out.close(); 
}
    

public String getTodayTime() {
    Date date = Calendar.getInstance().getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd   hh:mm:ss");
    return sdf.format(date);
}
}

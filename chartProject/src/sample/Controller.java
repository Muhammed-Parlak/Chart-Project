package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.event.ActionEvent;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

import java.io.*;


//kopya yerel patlıyo




public class Controller
{
    public int boyut;
    public BarChart barcharttable;

    private ArrayList<Line> line = new ArrayList<>();
    private ArrayList<String> txtVerileri=new ArrayList<>();

    @FXML
    public Label labfile;
    public LineChart<String,Number>  linechartTable;
    public CategoryAxis xAxisLabel;

    //dosya seçme kısmı için filtreli pencere açma kodu
    @FXML
    public void handledosyaSec(ActionEvent event) throws IOException, ParserConfigurationException, SAXException {

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("xml or txt files", "*.xml", "*.txt"));
        File f = fc.showOpenDialog(null);

        String fileName = f.getName();

        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, f.getName().length());  //tür ile işlem yapmak için hazırlandı

        //seçilen doyya label a yazdırıldı
        if (f != null) {
            labfile.setText(f.getAbsolutePath());

        }
        if(fileExtension.equals("xml"))
        {
            getData();
            //parcalaXML();

        }
        else{
            getdataTXT();
            parcalaTXT();
        }

    }
    public void handlelinechart(ActionEvent event) throws IOException, ParserConfigurationException, SAXException {
        setlineChart();
        linechartTable.setVisible(true);
        barcharttable.setVisible(false);


    }

    public void handlebarchart(ActionEvent event) throws IOException, ParserConfigurationException, SAXException {
        linechartTable.setVisible(false);
        barcharttable.setVisible(true);
    }

    public int getData() throws ParserConfigurationException, IOException, SAXException {


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new File(labfile.getText()));
        Element rootElement=document.getDocumentElement();
        NodeList records=rootElement.getElementsByTagName("record");
        for (int i = 0; i < records.getLength(); i++) {
            Element countryElement=(Element) records.item(i);
            //Verileri parçalıyor ve değişkenlere atılıyor.
            String Name=countryElement.getElementsByTagName("field").item(0).getTextContent();
            String Country=countryElement.getElementsByTagName("field").item(1).getTextContent();
            String Year=countryElement.getElementsByTagName("field").item(2).getTextContent();
            int Value=Integer.parseInt(countryElement.getElementsByTagName("field").item(3).getTextContent());
            String Catagory=countryElement.getElementsByTagName("field").item(4).getTextContent();
            line.add(new Line(Year, Name,Country, Value, Catagory));

        }
         //Print all ulkeler.
        for (Line ulk : line)
            System.out.println(ulk.toString());

        return 0;

    }



        public int getdataTXT() throws ParserConfigurationException, IOException, SAXException {

            try
            {
                File file=new File(labfile.getText());
                FileReader fr=new FileReader(file);
                BufferedReader br=new BufferedReader(fr);
                StringBuffer sb=new StringBuffer();
                String line;
                txtVerileri.clear();
                while((line=br.readLine())!=null)
                {
                    try {
                        int a =Integer.parseInt(line.substring(0,4)); // int mi
                        txtVerileri.add(line);
                        sb.append(line);
                        sb.append("\n");
                    }catch (Exception e){
                        continue;
                    }
                }
                fr.close();

            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

            return 0;
        }

    public String[] kopya =new String[5];
    public String[] kopya1 =new String[5];
    public ArrayList<String> gecisList=new ArrayList<>();
    public ArrayList<String> gecisList1=new ArrayList<>();


    public void parcalaXML() {
        boyut=(line.size());
        String[][] matris= new String[boyut][5];

        Collections.reverse(line);
        System.out.println(line.size());

        for (Line ulke : line) {
            kopya = ((ulke.toString()).split(",", 5));
            //5 li olarak ekleme sırayla
            for (int i=0;i<5;i++)
            gecisList.add(kopya[i]);
        }



        int sayac=0;
        for (int j = 0; j < boyut; j++) {
            for (int k = 0; k < 5; k++) {
                matris[j][k] = gecisList.get(sayac);
                sayac++;

            }
        }

        for (int j = 0; j < boyut; j++) {
            for (int k = 0; k < 5; k++) {
                System.out.print(matris[j][k]+" ");

            }
            System.out.println();
        }
        System.out.println(boyut);

    }
        public void parcalaTXT(){

            boyut=(txtVerileri.size());
            String[][] matris= new String[boyut][5];



            for (String ulke : txtVerileri) {
                kopya1 = ((ulke).split(",", 5));
                //5 li olarak ekleme sırayla

                line.add(new Line(kopya1[0],kopya1[1],kopya1[2],Integer.parseInt(kopya1[3]),kopya1[4]));

            }
            for (Line ulke: line) {
                System.out.println(ulke.toString());

            }

        }



    ArrayList<XYChart.Series> xyCharts=new ArrayList<>();
    ObservableList<String> Categories= FXCollections.observableArrayList();

    public void setlineChart(){
//tüm tarihleri belirtip Xaksislabel a atıyor
        Set<String> hash_Set = new LinkedHashSet<>();
        for(int a = 0; a<(line.size()); a++){

            hash_Set.add(line.get(a).getYear());

        }

        Categories.addAll(hash_Set);

        for(String str : hash_Set) {
            System.out.println(str);
        }




        xyCharts.add(new XYChart.Series<String,Number>());
        xyCharts.get(0).setName("a");

        System.out.println(xyCharts.size());

        int k=0;
        for (int j = 0; j< line.size(); j++){


            if (countryControl(line.get(j).getName(),xyCharts)){

                int b= indexControl(line.get(j).getName(),xyCharts);

                xyCharts.get(b).getData().add(new XYChart.Data<>(line.get(j).getYear(), line.get(j).getValue()));
                //xyCharts.get(k).setName(countries.get(j).getName());
            }

            else{
                xyCharts.add(new XYChart.Series<String,Number>());
                k++;
                xyCharts.get(k).getData().add(new XYChart.Data<>(line.get(j).getYear(), line.get(j).getValue()));
                xyCharts.get(k).setName(line.get(j).getName());
                System.out.println(xyCharts.size());
            }

        }System.out.println( xyCharts.get(1).getData().size());



        while (k>15){
            linechartTable.getData().add(xyCharts.get(k));
            System.out.println(xyCharts.get(k).getData().get(1));
            k--;
        }

        xAxisLabel.setCategories(Categories);

    }

    public boolean countryControl(String countryName,ArrayList<XYChart.Series> series  ){

        boolean resume=false;
        for (int i=0;i<series.size();i++){
            if (series.get(i).getName().equals(countryName)){return true;}

        }

        return resume;

    }

    public int indexControl(String countryName,ArrayList<XYChart.Series> series ){
        int index =-1;

        for(int i=0;i<series.size();i++){
            if (series.get(i).getName().equals(countryName)){
                return i;
            }
        }
        return index;
    }

}

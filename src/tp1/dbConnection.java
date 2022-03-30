/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tp1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author Alvin Giovani
 */
//kelas untuk mengkoneksikan ke database
public class dbConnection {
    
    public static Connection con;
    public static Statement stm;
    
    //fungsi konek
    public void connect(){//untuk membuka koneksi ke database
        try {
            //masukkan url
            String url ="jdbc:mysql://localhost/tp1_dpbo";
            //username root
            String user="root";
            //dan password dikosongkan
            String pass="";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,user,pass);
            stm = con.createStatement();
            System.out.println("koneksi berhasil;");
        } catch (Exception e) {
            System.err.println("koneksi gagal" +e.getMessage());
        }
    }
    
    public DefaultTableModel readTable(Boolean authorMode){
        DefaultTableModel dataTabel = null;
        try{
            //mengatur kolom pertama
            Object[] column = null;
            String sql = "";
            if(authorMode == false){
                Object[] tempcolumn = {"No", "Judul", "Author", "Deskripsi", "Image Path"};
                column = tempcolumn;
                String sqltemp = "Select img, judul,autor,deskripsi from buku";
                sql = sqltemp;
            }
            else{
                Object[] tempcolumn = {"No", "Nama", "Jumlah Buku", "Buku Terakhir","Image Path"};
                column = tempcolumn;
                String sqltemp = "Select img, nama,jml_buku,last_buku from author";
                sql = sqltemp;
            }
            
            //connect ke database
            connect();
            
            dataTabel = new DefaultTableModel(null, column);
            //masukkan querry untuk menampilkan atribut nama dan nim
            
            
            
            ResultSet res = stm.executeQuery(sql);
            
            //looping output isi(barus kedua dan seterusnya)
            int no = 1;
            while(res.next()){
                //masukkan nilai nomor nama dan nim
                Object[] hasil = new Object[5];
                hasil[0] = no;
                if(authorMode == false){
                    hasil[1] = res.getString("judul");
                    hasil[2] = res.getString("autor");
                    hasil[3] = res.getString("deskripsi");
                    hasil[4] = res.getString("img");
                }
                else{
                    hasil[1] = res.getString("nama");
                    hasil[2] = res.getString("jml_buku");
                    hasil[3] = res.getString("last_buku");
                    hasil[4] = res.getString("img");

                }
                
                no++;
//                System.out.print(hasil[1]);
                //memasukkan ke dalam tabel
                dataTabel.addRow(hasil);
            }
        }catch(Exception e){
            System.err.println("Read gagal " +e.getMessage());
        }
        return dataTabel;
    }
    
    //fungsi untuk memasukkan queryy
    public void Query(String inputan){
        
        try{
            //connect ke database
            connect();
            //masukkan query 
            String sql = inputan;
//            System.out.println(sql);
            //jalankan query
            stm.execute(sql);
            
        }catch(Exception e){
            System.err.println("Read gagal " +e.getMessage());
        }
        
    }
    
//    public byte getImage(String inputan){
//        
//        try{
//            //connect ke database
//            connect();
//            //masukkan query 
//            String sql = inputan;
////            System.out.println(sql);
//            //jalankan query
//            stm.execute(sql);
//            byte file = stm.getBytes("img");
//            
//            return file;
//        }catch(Exception e){
//            System.err.println("Read gagal " +e.getMessage());
//        }
//        return null;
//        
//    }
//    
//    public byte[] getGambar(String judul) throws SQLException{
//        try{
//            connect();
//            String sql = "Select img from buku where judul = '"+judul+"'";
//            ResultSet rs = stm.execute(sql);
//            byte[] file = null;
//            while(rs.next()){
//                file = rs.getBytes("img");
//            }
//            return file;
//        }catch(Exception e){
//            
//        }
//        return null;
//
//    }
}

//http://noon.tistory.com/m/1477
//�� �ڵ�� DB�������� ���̺� ����, ������ ���� �� �˻� �ϴ� �ڵ�
import java.sql.*;

public class JAVA_sql_CreateQueryInsertion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			String driverName="org.gjt.mm.mysql.Driver";//�巯�̹� �̸� ����
			String DBName="Network_TermProject_HanGeul_question";
			String dbURL="jdbc:mysql://localhost:3306/"+DBName;//URL ����
			String SQL="select * from questions;";
			String sqlCT="CREATE TABLE HanGuel_QUESTIONS("+
			"question varchar(1000) NOT NULL,"+
					"option varchar(100) NOT NULL,"+
			"answer varchar(2) NOT NULL"+
					"PRIMARY KEY(question, option)"+")";
			
			Class.forName(driverName); //����̹� �ε�
			Connection con =DriverManager.getConnection(dbURL,"root","");//����
			System.out.println("Mysql DB Connection.");
			
			Statement stmt = con.createStatement();
			// stmt.executeUpdate("CREATE DATABASE Network_TermProject_HanGeul_question");
			// System.out.println("������ ���̽��� mydb�� �����Ǿ����ϴ�.");
			
			// stmt.executeUpdate(sqLCT);
			System.out.println("Table Created");
			//data Insert
			/*
			 stmt.executeUpdate ("insert into HanGuel_QUESTIONS();
			 System.out.println("Insert Data");
			 */
			
			stmt.executeQuery(SQL);
			ResultSet result=stmt.executeQuery(SQL);
			while(result.next()){
				System.out.print(result.getString(1)+"\t");
			}
			
			con.close();
		}catch(Exception e){
			System.out.println("Mysql Server Not Connection.");
			e.printStackTrace();
		}

	}

}

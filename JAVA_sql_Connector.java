//db와 java를 연동만 시키는 간단한 코드
//출처 http://ra2kstar.tistory.com/134
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JAVA_sql_Connector {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			Connection con = null;
			
			con=DriverManager.getConnection("jdbc:mysql://localhost","root","12345");
			
			java.sql.Statement st=null;
			ResultSet rs=null;
			st=con.createStatement();
			rs=st.executeQuery("SHOW DATABASES");
			
			if(st.execute("SHOW DATABASES")){
					rs=st.getResultSet();
			}
			while(rs.next()){
				String str=rs.getNString(1);
				System.out.println(str);
			}
		}catch (SQLException sqex){
			System.out.println("SQLException: "+sqex.getMessage());
			System.out.println("SQLState: "+sqex.getSQLState());
		}
	}
}

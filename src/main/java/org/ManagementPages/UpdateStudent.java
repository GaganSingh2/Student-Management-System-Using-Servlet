package org.ManagementPages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateStudent extends HttpServlet
{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		//Fetch Data from Form
		String sname = req.getParameter("nm");
		int srollno = Integer.parseInt(req.getParameter("rn"));
		String scourse = req.getParameter("cr");
		Double sper = Double.parseDouble(req.getParameter("pr"));
		String splace = req.getParameter("pl");
		
		PrintWriter out = resp.getWriter();
		//JDBC Logic for Update the Data
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			//Load Driver and Register
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Registr");
			
			//Establish the Connection 
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/std_management_db_servlet_proj",
					"root",
					"admin");
			System.out.println("Establish Connection!!");
			
			//Create a Statement or Platform
			String sql = "UPDATE studentr SET course=?, throughout=?, placed=? WHERE rollno=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, scourse);
			pstmt.setDouble(2, sper);
			pstmt.setString(3, splace);
			pstmt.setInt(4, srollno);
			
			//Execute Query
			int cnt = pstmt.executeUpdate();
			out.println("<html><body bgcolor='blue'>");
			if(cnt==0) {
				out.println("<h1>Oh Sorry!! </h1>");
				out.println("<h3>Your Details Has Been Not Here!!</h3>");
			}
			else {
				out.println("<h1>Congratulations!! </h1>");
				out.println("<h3>Your Details Has Been Updated!!</h3>");
			}
			
			out.println("</body></html>");
			out.close();
		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

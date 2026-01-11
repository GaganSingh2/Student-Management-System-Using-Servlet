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


public class AddStudent extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		//Fetching Data From File
		int sid = Integer.parseInt(req.getParameter("rn"));
		String sname = req.getParameter("nm");
		String sdegree = req.getParameter("dg");
		String scourse = req.getParameter("cr");
		Double sper = Double.parseDouble(req.getParameter("pr"));
		int sout = Integer.parseInt(req.getParameter("po"));
		String splace = req.getParameter("pl");
		
		PrintWriter out = resp.getWriter();
		
		//JDBC Logic For Storing Data in DataBase
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			//Load Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Load Driver and Register");
			
			//Establish the Connection
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/std_management_db_servlet_proj",
					"root", "admin");
			System.out.println("Establish Connection");
			
			//Create a Statement or Platfrom
			String sql = "INSERT INTO studentr VALUES(?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, sid);
			pstmt.setString(2, sname);
			pstmt.setString(3, sdegree);
			pstmt.setString(4, scourse);
			pstmt.setDouble(5, sper);
			pstmt.setInt(6, sout);
			pstmt.setString(7, splace);
			
			
			//Execute SQL Query
			pstmt.executeUpdate();
			System.out.println("Student Details Added!!");
			
			out.println("<html><body bgcolor='orange'>");
			out.println("<h1>Congratulations "+sname+"!! </h1>");
			out.println("<h3>Your Details Has Been Registered!!</h3>");
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

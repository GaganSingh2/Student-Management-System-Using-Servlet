package org.ManagementPages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewStudent extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
	
		PrintWriter out = resp.getWriter();
		//JDBC Logic For Storing Data in DataBase
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			//Load Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Load Driver and Register");
			
			//Establish the Connection
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/std_management_db_servlet_proj",
					"root", "admin");
			System.out.println("Establish Connection");
			
			//Process The Resultant Data (SELECT Operation)
			String sqlSelect = "SELECT * FROM studentr";
			pstmt = con.prepareStatement(sqlSelect);
			rs = pstmt.executeQuery();
			
			out.println("<html><head><title>Student Data</title></head><body bgcolor='cyan'>");
		    out.println("<h2 align='center'>Student Details</h2>");

		    out.println("<table align='center' border='5' cellpadding='10' cellspacing='10' bgcolor='lightblue' width='500'>");
		    out.println("<tr>");
		    out.println("<th>Roll No</th>");
		    out.println("<th>Name</th>");
		    out.println("<th>Degree</th>");
		    out.println("<th>Course</th>");
		    out.println("<th>Percentage</th>");
		    out.println("<th>Passed Year</th>");
		    out.println("<th>Place</th>");
		    out.println("</tr>");

		    while (rs.next()) {
		        out.println("<tr>");
		        out.println("<td>" + rs.getInt("rollno") + "</td>");
		        out.println("<td>" + rs.getString("name") + "</td>");
		        out.println("<td>" + rs.getString("degree") + "</td>");
		        out.println("<td>" + rs.getString("course") + "</td>");
		        out.println("<td>" + rs.getDouble("throughout") + "</td>");
		        out.println("<td>" + rs.getInt("passedyear") + "</td>");
		        out.println("<td>" + rs.getString("placed") + "</td>");
		        out.println("</tr>");
		    }

		    out.println("</table>");
		    out.println("</body></html>");

		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
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

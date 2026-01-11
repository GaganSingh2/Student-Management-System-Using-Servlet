package org.ManagementPages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SearchStudent extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		int sid = Integer.parseInt(req.getParameter("rn"));
	
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
			String sqlSelect = "SELECT * FROM studentr WHERE rollno=?";

			
			pstmt = con.prepareStatement(sqlSelect);
			pstmt.setInt(1, sid);
			rs = pstmt.executeQuery();
			boolean flag = false;
		
			

		    while (rs.next()) {
		    	out.println("<html><head><title>Student Data</title></head><body bgcolor='cyan'>");
			    out.println("<h2 align='center'>Student Details</h2>");

			    out.println("<table border='1' align='center' cellpadding='10' bgcolor='lightblue'>");
			    out.println("<tr>");
			    out.println("<th>Roll No</th>");
			    out.println("<th>Name</th>");
			    out.println("<th>Degree</th>");
			    out.println("<th>Course</th>");
			    out.println("<th>Percentage</th>");
			    out.println("<th>Passed Year</th>");
			    out.println("<th>Place</th>");
			    out.println("</tr>");
		        out.println("<tr>");
		        out.println("<td>" + rs.getInt("rollno") + "</td>");
		        out.println("<td>" + rs.getString("name") + "</td>");
		        out.println("<td>" + rs.getString("degree") + "</td>");
		        out.println("<td>" + rs.getString("course") + "</td>");
		        out.println("<td>" + rs.getDouble("throughout") + "</td>");
		        out.println("<td>" + rs.getInt("passedyear") + "</td>");
		        out.println("<td>" + rs.getString("placed") + "</td>");
		        out.println("</tr>");
		        out.println("</table>");
			    out.println("</body></html>");
		        flag = true;
		    }

		    
		    
		    out.println("<html><body bgcolor='blue'>");
			if(!flag) {
				out.println("<h1>Oh Sorry!! </h1>");
				out.println("<h3>Your Details Has Been Not Here!!</h3>");
			}
			out.println("</body></html>");
			out.close();
		    
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

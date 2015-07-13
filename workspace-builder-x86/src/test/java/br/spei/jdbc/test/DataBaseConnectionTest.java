/** Copyright - 2015 - Paulo Henrique Ferreira de Lima - TechFull IT Services
Licensed under the Apache License, Version 2.0 (the “License”);
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an “AS IS” BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. */
package br.spei.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

/**
 * The Class DataBaseConnectionTest.
 */
public class DataBaseConnectionTest {

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(final String[] args) {

		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			Connection conn = DriverManager
					.getConnection("jdbc:sqlserver://localhost:1433;user=sa;"
							+ "password=ph@230711;databaseName=AFS Tools Client");

			// Print all warnings
			for (SQLWarning warn = conn.getWarnings(); warn != null; warn = warn
					.getNextWarning()) {
				System.out.println("SQL Warning:");
				System.out.println("State  : " + warn.getSQLState());
				System.out.println("Message: " + warn.getMessage());
				System.out.println("Error  : " + warn.getErrorCode());
			}

			// Prepare a statement
			PreparedStatement ps = conn
					.prepareStatement("SELECT * FROM USER_SYS");

			// Set the first parameter of the statement
			// ps.setString(1, "NAME_USER");

			// Execute the query
			ResultSet rs = ps.executeQuery();

			// Loop through the result set
			while (rs.next()) {
				System.out.println(rs.getString("ID_USER") + " "
						+ rs.getString("NAME_USER"));
			}

			// Close the result set, statement and the connection
			rs.close();
			ps.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}

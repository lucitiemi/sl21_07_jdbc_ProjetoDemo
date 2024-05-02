package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;


public class Program {

	public static void main(String[] args) {
		
		// PDF 21 - DEMONSTRACOES - operacoes com o BD
		
		Connection conn = null;
		Statement st = null;
		
		try {
			
			// fazendo a conexão
			conn = DB.getConnection();
			
			
			// nao deixa confirmar as operacoes automaticamente
			conn.setAutoCommit(false);
			
			
			st = conn.createStatement();
			
			
			// operacao 1
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
			
			
			/* 
			// gerando um erro par teste
			int x = 1;
			if (x<2) {
				throw new SQLException("Fake error");
			}
			*/
			
			
			// operacao 2
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
			
			
			// solicita uma confirmaçao
			conn.commit();
			
			
			System.out.println("rows1: " + rows1);
			System.out.println("rows2: " + rows2);

			
		}
		catch (SQLException e) {
			
			try {
				// desfaz o que já foi feito
				conn.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
			} 
			catch (SQLException e1) {
				throw new DbException("Erros trying to rollback! Caused by " + e1.getMessage());
				
			}
			
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}


	}

}

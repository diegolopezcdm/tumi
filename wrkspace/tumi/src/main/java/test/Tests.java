package test;

import com.sv.tumi.db.dao.EvaluacionDAO;

public class Tests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		PaisDAO dao= new PaisDAO();
//		Pais pais = new Pais();
//		pais.setArea("503");
//		pais.setMoneda("$");
//		pais.setNombre("DESC");
//		pais.setSimbolomonetario("$");
//		
//		dao.create(pais);
//		Date[] rango = {new Date(), new Date()};
//		EmpleadoDAO em = new EmpleadoDAO();
//		Empleado idEmpleado = em.find(9);
//		System.out.println("empleado "+idEmpleado);

		EvaluacionDAO edao = new EvaluacionDAO();
		System.out.println(edao.find(1).getUsuarioRegistro());
		
	}

	
}

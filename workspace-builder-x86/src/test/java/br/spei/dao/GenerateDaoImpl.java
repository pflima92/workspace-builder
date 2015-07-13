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
package br.spei.dao;

import java.util.ArrayList;

/**
 * The Class GenerateDaoImpl.
 */
public class GenerateDaoImpl {

	/** The Constant NOME_TABELA. */
	private final static String NOME_TABELA = "HEALTH_PLAN";

	/** The Constant NUMERICO. */
	private final static String NUMERICO = "NUMERICO";

	/** The Constant ID_PRINCIPAL. */
	private final static String ID_PRINCIPAL = "ID_HEALTH_PLAN";

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(final String[] args) {

		ArrayList<DataBean> lista = new ArrayList<>();

		// lista.add(new DataBean("ID_PATIENT", "?",NUMERICO));
		lista.add(new DataBean("NAME", "?"));
		lista.add(new DataBean("SIGLA", "?"));

		String cmd = "INSERT INTO " + NOME_TABELA + " (";
		int i = 0;
		int size = lista.size();

		for (DataBean dataBean : lista) {
			i++;
			String prop = dataBean.getNomeCampo();
			if (i == size) {
				cmd += prop + ") VALUES (";
			} else {
				cmd += prop + ",";
			}
		}
		i = 0;
		for (DataBean dataBean : lista) {
			i++;
			String prop = "";
			if (dataBean.getTipo().equals(NUMERICO)) {
				prop = dataBean.getValue();
			} else {
				if (dataBean.getValue().equals("?")) {
					prop = dataBean.getValue();
				} else {
					prop = "'" + dataBean.getValue() + "'";
				}
			}
			if (i == size) {
				cmd += prop + ")";
			} else {
				cmd += prop + ",";
			}
		}

		System.out.println(cmd);

		// ps.setString(1, user.getName());
		i = 0;
		String defaultString = "ps.setString(";
		for (DataBean dataBean : lista) {
			i++;
			System.out.println(defaultString + i + ", obj.get());");
		}

		cmd = "\nUPDATE " + NOME_TABELA + " SET ";

		i = 0;
		for (DataBean dataBean : lista) {
			i++;
			String prop = "";
			if (dataBean.getTipo().equals(NUMERICO)) {
				prop = dataBean.getValue();
			} else {
				if (dataBean.getValue().equals("?")) {
					prop = dataBean.getValue();
				} else {
					prop = "'" + dataBean.getValue() + "'";
				}
			}
			if (i == size) {
				cmd += dataBean.getNomeCampo() + " = " + prop;
			} else {
				cmd += prop + ",";
			}
		}

		cmd += " WHERE = " + ID_PRINCIPAL + " ?";

		System.out.println(cmd);

		// ps.setString(1, user.getName());
		i = 0;
		defaultString = "ps.setString(";
		for (DataBean dataBean : lista) {
			i++;
			if (dataBean.getTipo().equals(NUMERICO)) {
				System.out.println("ps.setInt(" + i + ", obj.get());");
			}
			System.out.println(defaultString + i + ", obj.get());");
		}

		cmd = "\nDELETE FROM " + NOME_TABELA + " WHERE " + ID_PRINCIPAL + " = "
				+ "?";
		System.out.println(cmd);
		System.out.println(defaultString + i + ", obj.get());");

		cmd = "\nSELECT * FROM " + NOME_TABELA + " WHERE " + ID_PRINCIPAL
				+ " = " + "?";
		System.out.println(cmd);
		System.out.println(defaultString + i + ", obj.get());");

		cmd = "\nSELECT * FROM " + NOME_TABELA;
		System.out.println(cmd);

	}

}

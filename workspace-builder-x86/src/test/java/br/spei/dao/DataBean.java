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

/**
 * The Class DataBean.
 */
public class DataBean {

	/** The nome campo. */
	private String nomeCampo;

	/** The value. */
	private String value;

	/** The tipo. */
	private String tipo;

	/**
	 * Instantiates a new data bean.
	 * 
	 * @param nomeCampo
	 *            the nome campo
	 * @param value
	 *            the value
	 */
	public DataBean(final String nomeCampo, final String value) {
		this.nomeCampo = nomeCampo;
		this.value = value;
	}

	/**
	 * Instantiates a new data bean.
	 * 
	 * @param nomeCampo
	 *            the nome campo
	 * @param value
	 *            the value
	 * @param tipo
	 *            the tipo
	 */
	public DataBean(final String nomeCampo, final String value,
			final String tipo) {
		this.nomeCampo = nomeCampo;
		this.value = value;
		this.tipo = tipo;
	}

	/**
	 * Gets the nome campo.
	 * 
	 * @return the nome campo
	 */
	public String getNomeCampo() {
		return nomeCampo;
	}

	/**
	 * Gets the tipo.
	 * 
	 * @return the tipo
	 */
	public String getTipo() {
		if (tipo == null) {
			return "string";
		}
		return tipo;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the nome campo.
	 * 
	 * @param nomeCampo
	 *            the new nome campo
	 */
	public void setNomeCampo(final String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}

	/**
	 * Sets the tipo.
	 * 
	 * @param tipo
	 *            the new tipo
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(final String value) {
		this.value = value;
	}

}

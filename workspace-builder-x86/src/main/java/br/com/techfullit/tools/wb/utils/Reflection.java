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
package br.com.techfullit.tools.wb.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


/**
 * Classe utilitária com métodos que envolvem reflexão de objetos.
 * 
 * Classe: Reflection.java
 * @author DTS Consulting
 * @version $Reversion$
 */
public class Reflection {

    /** format - {@link SimpleDateFormat}. */
	private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * Recupera os fields de uma classe, inclusive da super classe.
     * 
     * @param classe - {@link Class} classe
     * @return {@link List}
     */
    public static List<Field> getFields(final Class<?> classe) {
        
        // Recuperando as propriedades da classe
        List<Field> fields = new ArrayList<Field>();
        
        if (classe.getDeclaredFields() != null) {
        	fields.addAll(Arrays.asList(classe.getDeclaredFields()));
        	
        	if (classe.getSuperclass() != null) {
        		fields.addAll(getFields(classe.getSuperclass()));
        	}
        }
        
        return fields;
    }

    /**
     * Método que retorna o valor de uma propriedade de um bean.
     * 
     * @param bean - {@link Object} bean
     * @param property - {@link String} propriedade
     * @return {@link Object}
     * @throws Exception - {@link Exception} Exceção ao se recuperar valor do bean.
     */
    public static Object getValue(
            final Object bean, 
            final String property) throws Exception {

        return getValue(bean, property, new Class[] {}, new Object[] {});
    }

    /**
	 * Transforma um valor String em um objeto, de acordo com tipo passado.
	 *
	 * @param bean
	 *            the bean
	 * @param property
	 *            the property
	 * @param params
	 *            the params
	 * @param args
	 *            the args
	 * @return {@link Object} ou null
	 * @throws Exception
	 *             - {@link Exception}
	 */
 

    /**
     * Método que retorna o valor de uma propriedade de um bean.
     * 
     * @param bean - {@link Object} bean
     * @param property - {@link String} propriedade
     * @param params - array de {@link Class} Tipo de parametros
     * @param args - array de {@link Object} Objetos que preenchem os parametros
     * @return {@link Object}
     * @throws Exception - {@link Object} Exceção ao se recuperar valor do bean.
     */
    public static Object getValue(
            final Object bean, 
            final String property,
            final Class<?>[] params,
            final Object[] args) throws Exception {

        Method met = bean.getClass()
                .getMethod("get"
                        + property.substring(0, 1).toUpperCase()
                        + property.substring(1), params);

        return met.invoke(bean, args);
    }
    
    /**
     * Método que atribui um valor para uma propriedade.
     * 
     * @param object - {@link Object} object
     * @param property - {@link String} property
     * @param param - array de {@link Class} params
     * @param args - array de {@link Object} arguments
     * @return {@link Object}
     * @throws Exception - {@link Exception}
     */
    public static Object setValue(
            final Object object, 
            final String property,
            final Class<?>[] param, 
            final Object[] args) throws Exception {


        Method met = 
            object.getClass().getMethod("set" 
                    + property.substring(0, 1).toUpperCase()
                    + property.substring(1), 
                    param);

        return met.invoke(object, args);
    }
   
    /**
	 * To string.
	 *
	 * @param object
	 *            the object
	 * @return {@link String}
	 */
    public static String toString(Object object) {
    	
    	if (object == null) {
    		return "";
    	}
       
        String className = object.getClass().getName();
        className = className.substring((className.lastIndexOf(".") + 1));
        
        StringBuffer sb = new StringBuffer("\n\n============= [ ").append(className).append(" - INICIO ] =============\n");
        
        if (object instanceof List<?>) {
			Iterator<?> it = ((List<?>) object).iterator();
			while (it.hasNext()) {
				toString(it.next());
			}
		} else {
        
	        List<Field> fields = null;
			try {
				fields = Reflection.getFields(object.getClass());
			} catch (Exception e1) { }
	        try {
	        
		        if (fields == null || fields.isEmpty()) {
		        	if("java.util.Date".equalsIgnoreCase(className)) {
		        		sb.append("\n \t=> ").append(format.format(object));
//		        	} else if("java.util.List".equalsIgnoreCase(className) || "java.util.ArrayList".equalsIgnoreCase(className)) {
//		        		for (int i=0; i<((List)object).size(); i++) {
//		        			toString(((List)object).get(i));
//		        		}
		        	} else {
		        		sb.append("\n \t=> ").append(object);
		        	}
		        } else {
		        	
		        	if(className.indexOf("java.lang") > -1) {
		        		sb.append("\n \t=> ").append((object == null) ? "" : object);
		        	} else {
		        	
			        	 Collections.sort(fields, new Comparator<Object>() {
			                 @Override
							public int compare(Object f1, Object f2) {
			                     return ((Field)f1).getName().compareTo(((Field)f2).getName());
			                 }
			             });
			        	 Field field = null;
			        	 for (int i = 0; i < fields.size(); i++) {
			
			                field = fields.get(i);
			                String property = field.getName();
			                
			                Object value = null;
			                
			                if (field.getModifiers() == Modifier.PRIVATE || field.getModifiers() == Modifier.PROTECTED) {
			                    try {
									value = Reflection.getValue(object, property, new Class[] {}, new Object[]{});
								} catch (Exception e) { continue; }
			                    
			                    if("java.util.Date".equalsIgnoreCase(field.getType().getName())) {
			                    	sb.append("\n \t=> ").append(property).append(" = ");
			                    	sb.append((value == null) ? "" : format.format(value));
			                    } else {
			                    	sb.append("\n \t=> ").append(property).append(" = ").append((value == null) ? "" : value);
			                    }
			                }
			            }
		        	}
		        }
            
	        } catch (Exception e) {
	        	sb = new StringBuffer("\n\nErro ao gerar toString() da classe " + object.getClass().getName());
	        	sb.append("\nErro: ").append(e);
	        } finally {
	        	sb.append("\n\n============= [ ").append(className).append(" - FIM ] =============\n\n");
	        }
		}

        System.out.println(sb.toString());
        
        return sb.toString();
    }
    
    
}

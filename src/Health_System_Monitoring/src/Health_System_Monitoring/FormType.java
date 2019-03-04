/**
 * 
 */
package Health_System_Monitoring;

/**
 * @author user
 *
 */
public enum FormType {
		FT_INT {
	        public String toString() {
	            return "Int";
	        }
		},
		FT_FLOAT {
	        public String toString() {
	            return "Float";
	        }
		},
		FT_STRING {
	        public String toString() {
	            return "String";
	        }
		},
		FT_BOOLEAN {
	        public String toString() {
	            return "Boolean";
	        }
		}
}

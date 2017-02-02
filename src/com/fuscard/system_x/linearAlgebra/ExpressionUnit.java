package com.fuscard.system_x.linearAlgebra;

/**
 * A unit added to the expression builder through an expression builder method.
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public interface ExpressionUnit {

    public class Plus implements ExpressionUnit {

        @Override
        public String toString() {
            return "+";
        }

    }

    public class Minus implements ExpressionUnit {

        @Override
        public String toString() {
            return "-";
        }

    }

    public class Term implements ExpressionUnit, Cloneable {

        private int coefficient;
        private Variable variable;
        /**
         * Whether this term is preceded by a plus or a minus
         */
        private boolean positive = true;

        public Term(int coefficient, Variable variable) {
            this.coefficient = coefficient;
            this.variable = variable;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone(); //To change body of generated methods, choose Tools | Templates.
        }
        
        

        public int getCoefficient() {
            return coefficient;
        }

        public int getNormalizedCoefficient() {
            int c = getCoefficient();
            if (!isPositive()) {
                c = 0 - c;
            }
            return c;
        }

        public void setCoefficient(int coefficient) {
            this.coefficient = coefficient;
        }

        public void add(Term t) {
            if (t.getVariable() != getVariable()) {
                throw new IllegalArgumentException("Cannot add two terms with different variables");
            }

            int c = t.getNormalizedCoefficient();
            int d = getNormalizedCoefficient();

            int e = c + d;

            if (e < 0) {
                setPositive(false);
                setCoefficient(0 - e);
            } else {
                setCoefficient(e);
                setPositive(true);
            }
        }

        public Variable getVariable() {
            return variable;
        }

        @Override
        public String toString() {
            return toString(true);
        }

        public String toString(boolean showSign) {
            String v = variable == null ? "" : variable.toString();
            if ("".equals(v)) {
                v = coefficient + v;
            } else {
                if (coefficient == -1) {
                    v = "-" + v;
                } else if (coefficient != 1) {
                    v = coefficient + v;
                }
            }
            if (showSign) {
                v = (isPositive() ? "+" : "-") + " " + v;
            }
            return v;
        }

        /**
         * @return the positive
         */
        public boolean isPositive() {
            return positive;
        }

        /**
         * @param positive the positive to set
         */
        public void setPositive(boolean positive) {
            this.positive = positive;
        }

    }

    public class Equals implements ExpressionUnit {

        @Override
        public String toString() {
            return "=";
        }
    }

}

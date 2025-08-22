package com.example.appcalculadora.model;

public class Calculadora {

    private String minivisor = "0";
    private final int MAX_DIG = 12;
    private String nrVisor = "0";
    private boolean ptDecimal = false;
    private boolean iniciouSegundo = false;
    private boolean estadoErro = false;
    private boolean overflow = false;
    private boolean depoisDeIgual = false;
    private String memTemp = "";
    private Op op = Op.NOP;

    public Calculadora() {
    }

    // Retorna a operação atual
    public Op getOp() {
        return op;
    }

    // Retorna o número do visor
    public String mostraVisor() {
        if (estadoErro) {
            nrVisor = "0";
            return "ERRO!";
        } else if (overflow) {
            nrVisor = "0";
            return "OVERFLOW";
        }
        if (this.nrVisor.isEmpty()) {
            nrVisor = "0";
        }
        return nrVisor;
    }

    // Retorna o conteúdo do minivisor
    public String getMinivisor() {
        StringBuilder mini = new StringBuilder();
        if (!iniciouSegundo) {
            mini.append(nrVisor);
        } else {
            mini.append(memTemp);
        }

        if (op != Op.NOP) {
            String simbolo = "";
            switch (op) {
                case SUM:
                    simbolo = " + ";
                    break;
                case SUB:
                    simbolo = " - ";
                    break;
                case MULT:
                    simbolo = " X ";
                    break;
                case DIV:
                    simbolo = " / ";
                    break;
            }
            mini.append(simbolo);
        }

        if (iniciouSegundo) {
            mini.append(nrVisor);
        }

        return mini.toString();
    }

    // Insere um dígito (ou ponto)
    public void insereDigito(String dig) {
        if (estadoErro) return;
        if (depoisDeIgual) {
            depoisDeIgual = false;
            nrVisor = "0";
        }
        if (!iniciouSegundo && op != Op.NOP) {
            iniciouSegundo = true;
            ptDecimal = false;
            nrVisor = "0";
        }
        if (nrVisor.length() == MAX_DIG) return;
        if (dig.equals(".")) {
            if (this.ptDecimal) return;
            this.ptDecimal = true;
        }
        if (nrVisor.equals("0")) {
            nrVisor = dig.equals(".") ? "0." : dig;
        } else {
            nrVisor += dig;
        }
    }

    // Define uma operação aritmética
    public void defineOperacao(Op operacao) {
        if (estadoErro) return;
        if (op == Op.NOP) {
            memTemp = nrVisor;
        }
        op = operacao;

    }

    // Ação da tecla IGUAL
    public void igual() {
        if (estadoErro) return;
        if (op == Op.NOP) return;
        double num1 = Double.parseDouble(memTemp);
        double num2 = Double.parseDouble(nrVisor);
        double resultado = 0;
        String strResultado = "";
        switch (op) {
            case DIV:
                if (num2 == 0) {
                    estadoErro = true;
                    return;
                }
                resultado = num1 / num2;
                break;
            case MULT:
                resultado = num1 * num2;
                if (Math.abs(resultado) > 999999999.0) {
                    overflow = true;
                    return;
                }
                break;
            case SUB:
                resultado = num1 - num2;
                break;
            case SUM:
                resultado = num1 + num2;
                break;
        }
        op = Op.NOP;
        iniciouSegundo = false;
        memTemp = "";
        if (resultado == (int) resultado) {
            strResultado = String.valueOf((int) resultado);
        } else {
            strResultado = String.valueOf(resultado);
        }
        nrVisor = strResultado.substring(0, Math.min(strResultado.length(), 12));
        depoisDeIgual = true;
    }

    // Tecla C
    public void teclaC() {
        nrVisor = "0";
        ptDecimal = false;
        iniciouSegundo = false;
        memTemp = "";
        op = Op.NOP;
        estadoErro = false;
        overflow = false;
    }

    // Tecla BK
    public void teclaBk() {
        if (nrVisor.length() == 1) {
            if (!nrVisor.equals("0")) {
                nrVisor = "0";
            }
            return;
        }
        if (nrVisor.charAt(nrVisor.length() - 1) == '.') {
            ptDecimal = false;
        }
        nrVisor = nrVisor.substring(0, nrVisor.length() - 1);
    }

    // Tecla ^2
    public void quadrado() {
        double resultado = 0;
        String strResultado = "";
        resultado = Math.pow(Double.parseDouble(nrVisor), 2);
        if (resultado > 999999999.0) {
            overflow = true;
            return;
        }
        if (resultado == (int) resultado) {
            strResultado = String.valueOf((int) resultado);
        } else {
            strResultado = String.valueOf(resultado);
        }
        nrVisor = strResultado.substring(0, Math.min(strResultado.length(), 12));
    }

    // Tecla Raiz Quadrada
    public void raizQuadrada() {
        if (Double.parseDouble(nrVisor) < 0) {
            estadoErro = true;
            nrVisor = "0";
            return;
        }
        double resultado = 0;
        String strResultado = "";
        resultado = Math.sqrt(Double.parseDouble(nrVisor));
        if (resultado == (int) resultado) {
            strResultado = String.valueOf((int) resultado);
        } else {
            strResultado = String.valueOf(resultado);
        }
        nrVisor = strResultado.substring(0, Math.min(strResultado.length(), 12));
    }
}

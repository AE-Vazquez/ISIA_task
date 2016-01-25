/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matrices;

import java.awt.Dimension;
import java.util.Random;

/**
 *
 * @author galvez
 */
public class Matriz {
    private int[][]datos;
    private Random rnd = new Random();
    
    public Matriz(int filas, int columnas, boolean inicializarAleatorio){
        datos = new int[columnas][];
        for(int i=0; i<columnas; i++){
            datos[i] = new int[filas];
            if (inicializarAleatorio)
                for(int j=0; j<filas; j++)
                    datos[i][j] = rnd.nextInt(100);
        }
    }
    public Matriz(Dimension d, boolean inicializarAleatorio){
        this(d.height, d.width, inicializarAleatorio);
    }
    
    public Dimension getDimension(){
        return new Dimension(datos.length, datos[0].length);
    }
    
    public static Matriz sumarDosMatrices(Matriz a, Matriz b) throws DimensionesIncompatibles { 
        if(! a.getDimension().equals(b.getDimension())) throw new DimensionesIncompatibles("La suma de matrices requiere matrices de las mismas dimensiones");        
        int i, j, filasA, columnasA; 
        filasA = a.getDimension().height; 
        columnasA = a.getDimension().width; 
        Matriz matrizResultante = new Matriz(filasA, columnasA, false);
        for (j = 0; j < filasA; j++) { 
            for (i = 0; i < columnasA; i++) { 
                matrizResultante.datos[i][j] += a.datos[i][j] + b.datos[i][j]; 
            } 
        } 
        return matrizResultante; 
    }
    
    public static Matriz multiplicarMatrices(Matriz a, Matriz b) throws DimensionesIncompatibles { 
        if(a.getDimension().width!=b.getDimension().height) throw new DimensionesIncompatibles("La suma de matrices requiere matrices de las mismas dimensiones");        
        int i, j,k, filasA, columnasA, columnasB; 
        filasA = a.getDimension().height; 
        columnasA = a.getDimension().width;
        columnasB = b.getDimension().width; 
        
        Matriz matrizResultante = new Matriz(filasA, columnasB, false);
        for (j = 0; j < filasA; j++) { 
            for (i = 0; i < columnasB; i++) { 
            	for(k=0;k<columnasA;k++)
            	{
            		matrizResultante.datos[i][j] += a.datos[i][k] * b.datos[k][j];
            	}
               
            } 
        } 
        return matrizResultante; 
    }

public static Matriz invertir(Matriz m) throws DimensionesIncompatibles {
		
		int det=determinanteMatriz(m);
		Matriz traspuesta = traspuesta(m);
		
		for(int i=0;i<traspuesta.getDimension().height;i++)
		{
			for(int j=0;j<traspuesta.getDimension().width;j++)
			{
				traspuesta.datos[i][j]=traspuesta.datos[i][j]/det;
			}
		}
		
		return traspuesta;
		
	}

	public static Matriz traspuesta(Matriz m) {
		int filasA, columnasA;
		filasA = m.getDimension().height;
		columnasA = m.getDimension().width;
		Matriz matrizTraspuesta = new Matriz(filasA, columnasA, false);

		for (int i = 0; i < filasA; i++) {
			for (int j = 0; j < columnasA; j++) {
				matrizTraspuesta.datos[j][i] = m.datos[i][j];
			}
		}
		return matrizTraspuesta;
	}

	public static int determinanteMatriz(Matriz m) throws DimensionesIncompatibles {
		int filas, columnas;
		filas = m.getDimension().height;
		columnas = m.getDimension().width;
		if (filas != columnas) {
			throw new DimensionesIncompatibles("La matriz debe ser cuadrada");
		}
		if (filas == 2) {
			return (m.datos[0][0] * m.datos[1][1]) - (m.datos[0][1] * m.datos[1][0]);

		}

		int sum = 0;
		for (int i = 0; i < columnas; i++) {
			sum += signo(i) * m.datos[0][i] * determinanteMatriz(subMatriz(m, 0, i));
		}
		return sum;

	}

	public static Matriz subMatriz(Matriz m, int fila, int columna) {
		int filas, columnas;
		filas = m.getDimension().height;
		columnas = m.getDimension().width;
		Matriz mat = new Matriz(filas - 1, columnas - 1, false);
		int r = -1;
		for (int i = 0; i < filas; i++) {
			if (i == fila)
				continue;
			r++;
			int c = -1;
			for (int j = 0; j < columnas; j++) {
				if (j == columna)
					continue;
				mat.datos[r][++c] = mat.datos[i][j];
			}
		}
		return mat;
	}
	
	public static int signo(int n) {
		if (n % 2 == 0) {
			return -1;
		}

		return 1;

	}
    @Override
    public String toString(){
        String ret = "";
        ret += "[\n";
        for (int i = 0; i < getDimension().width; i++) {
            ret += "(";
            for (int j = 0; j < getDimension().height; j++) {  
                ret += String.format("%3d", datos[i][j]); 
                if (j != getDimension().height - 1) ret += ", ";
            } 
            ret += ")";
            if (i != getDimension().width - 1) ret += ",";
            ret += "\n";
        } 
        ret += "]\n";
        return ret;
    }
}

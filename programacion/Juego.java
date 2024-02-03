package programacion;

import java.util.Scanner;

public class Juego {

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    final String LIBRE = " L ";
    final String BLANCAS = " B ";
    final String NEGRAS = " N ";
    int filasTablero = 8;
    int columnasTablero = 8;
    int contadorBlancas = 0;
    int contadorNegras = 0;
    String[][] tablero;
    Scanner scanner = new Scanner (System.in);
    boolean quedanFichas = false;

    Juego(){
        rellenarTablero();
        leyendaInicial();
    }

    public static void leyendaInicial (){
        System.out.println();
        System.out.println();
        System.out.println( ANSI_YELLOW + "PRACTICA PRESENCIAL" + ANSI_WHITE);
        System.out.println("OSCAR URBON RISUEÑO");
        System.out.println("COSMIN RUSU");
        System.out.println("GUSTAVO DIAZ BIELSER");
        System.out.println();
        System.out.println(ANSI_CYAN + "L:  LIBRE       N:  NEGRA      B:  BLANCA" + ANSI_WHITE);
    }

    public void rellenarTablero(){
        tablero = new String[filasTablero][columnasTablero];
            for (int i = 0; i < 8; i ++ ){
                for(int j = 0; j < 8; j++ ){
                    if (i >= 5 && (j + i)%2!=0 ){
                        tablero[i][j]= BLANCAS;
                    } else if (i <= 2 && (j + i)%2!=0){
                        tablero[i][j] = NEGRAS;
                    }
                    else {
                        tablero[i][j]=LIBRE;
                    }
                }
            }
    }
    
    public void pintarTablero() {
        System.out.println();
        for (int i = 0; i < filasTablero; i++) {
            for (int j = 0; j < columnasTablero; j++) {
                System.out.print(tablero[i][j]);
            }
            System.out.println();
        }
    }

    public void jugar() {
        System.out.println();
        pintarTablero();
        
        System.out.println();  
        moverBlancas();    
        
        System.out.println();
        pintarTablero();

        System.out.println();
        moverNegras();
    }

    public void moverBlancas(){
        int filaActual=0;
        int columnaActual=0;
        String posicionActual;
        System.out.println("Es el turno del " + ANSI_RED + "JUGADOR 1." + ANSI_WHITE + "Juega con las BLANCAS");
        System.out.println("Selecciona la ficha que quieres MOVER");
        do {
            System.out.println("Escribe dos numeros seguidos. El primero sera la " + ANSI_PURPLE + "FILA" + ANSI_WHITE + " y el segundo la " + ANSI_CYAN + "COLUMNA." + ANSI_WHITE);
            posicionActual = scanner.nextLine();
        }while(comprobacionActualBlancas(posicionActual));
        filaActual = Character.getNumericValue(posicionActual.charAt(0));
        columnaActual = Character.getNumericValue(posicionActual.charAt(1));
        int filaNueva;
        int columnaNueva;
        String posicionNueva;
        System.out.println("Introduce el NUMERO de la casilla a la que te quieres MOVER");
        do{
            System.out.println("Escribe DOS numeros seguidos. Primero la " + ANSI_PURPLE + "FILA" + ANSI_WHITE + " y después la " + ANSI_CYAN +  "COLUMNA." + ANSI_WHITE);
            System.out.println("Si prefieres abandonar, escribe " + ANSI_GREEN + "EXIT" + ANSI_WHITE);
            posicionNueva = scanner.nextLine();
            if (posicionNueva.equals("EXIT")){
                System.out.println("El " + ANSI_RED + "JUGADOR 1." + ANSI_WHITE + " ha abandonado. EL ganador es el " + ANSI_BLUE + "JUGADOR 2." + ANSI_WHITE);
                System.exit(0);
            }
        } while (comprobacionMovimientoBlancas(posicionActual, posicionNueva));
            filaNueva = Character.getNumericValue(posicionNueva.charAt(0));
            columnaNueva = Character.getNumericValue(posicionNueva.charAt(1));
            if (tablero[filaNueva][columnaNueva] == LIBRE){
                tablero[filaActual][columnaActual] = LIBRE;
                tablero[filaNueva][columnaNueva] = BLANCAS;
            } else if(tablero[filaNueva][columnaNueva] == NEGRAS){
                tablero[filaActual][columnaActual] = LIBRE;
                tablero[filaNueva][columnaNueva] = LIBRE;
                contadorBlancas++;
                if (columnaNueva < columnaActual){
                    tablero[filaNueva - 1][columnaNueva -1] = BLANCAS;
                } else if (columnaNueva > columnaActual){
                    tablero[filaNueva - 1][columnaNueva +1] = BLANCAS;
                }
            }
        comprobarVictoria();
        System.out.println("FIN del turno de BLANCAS");
        System.out.println("Ya se ha comido " + ANSI_YELLOW + contadorBlancas + ANSI_WHITE + " fichas");
        }
  
    public boolean comprobacionMovimientoBlancas(String posicionActual, String posicionNueva){
        int filaActual;
        int columnaActual;
        int filaNueva;
        int columnaNueva;
        filaNueva = Character.getNumericValue(posicionNueva.charAt(0));
        columnaNueva = Character.getNumericValue(posicionNueva.charAt(1));
        if (!posicionNueva.matches("\\d{2}") || filaNueva > 7 || filaNueva < 0 || columnaNueva < 0 || columnaNueva > 7 ||  Integer.parseInt(posicionNueva) < 00){
            System.out.println(ANSI_RED + "ERROR. Introduce una casilla EXISTENTE." + ANSI_WHITE);
            return true;
        }
        filaActual = Character.getNumericValue(posicionActual.charAt(0));
        columnaActual = Character.getNumericValue(posicionActual.charAt(1));
        if (filaNueva >= filaActual ){
            System.out.println(ANSI_RED + "ERROR. El movimiento debe AVANZAR"+ ANSI_WHITE);
            return true;
        } else if ( columnaNueva == columnaActual ){
            System.out.println(ANSI_RED + "ERROR. El movimiento debe ser DIAGONAL."+ ANSI_WHITE);
            return true;
        } else if ((filaActual-filaNueva)!=1){
            System.out.println(ANSI_RED + "ERROR. El movimiento solo puede ser de UNA casilla."+ ANSI_WHITE);
            return true;
        } else if (columnaActual - columnaNueva != 1 && columnaActual - columnaNueva != -1){
            System.out.println(ANSI_RED + "ERROR. El movimiento solo puede ser de UNA casilla."+ ANSI_WHITE);
            return true;
        }else if (tablero[filaNueva][columnaNueva] == BLANCAS){
                System.out.println(ANSI_RED + "ERROR. Ya hay una ficha blanca ahí."+ ANSI_WHITE);
                return true;
        }
        System.out.println("Movimiento " + ANSI_YELLOW + "VALIDO" + ANSI_WHITE);
        return false;
    }

    public boolean comprobacionActualBlancas(String x){
        if (!x.matches("\\d{2}") || Integer.parseInt(x) > 77|| Integer.parseInt(x) < 00){
            System.out.println(ANSI_RED + "ERROR. Introduce el numero de una casilla EXISTENTE." + ANSI_WHITE);
            return true;
        }else{
            int filaComprobar;
            int columnaComprobar;
            filaComprobar = Character.getNumericValue(x.charAt(0));
            columnaComprobar = Character.getNumericValue(x.charAt(1));
            if ( tablero[filaComprobar][columnaComprobar] != BLANCAS){
                System.out.println(ANSI_RED + "ERROR. Elige una posicion donde haya una ficha BLANCA." + ANSI_WHITE);
                return true;
            }
            System.out.println("Seleccion " + ANSI_YELLOW + "CORRECTA"+ ANSI_WHITE);
            return false;
        }
    }

    public void moverNegras(){
        int filaActual;
        int columnaActual;
        String posicionActual;
        System.out.println("Es el turno del " + ANSI_BLUE + "JUGADOR 2." + ANSI_WHITE + ". Juega con las NEGRAS");
        System.out.println("Selecciona la ficha que quieres MOVER");
        do {
            System.out.println("Escribe DOS numeros seguidos. El primero sera la " + ANSI_PURPLE + "FILA" + ANSI_WHITE + " y el segundo la " + ANSI_CYAN + "COLUMNA." + ANSI_WHITE);
            posicionActual = scanner.nextLine();
        }while(comprobacionActualNegras(posicionActual));
        filaActual = Character.getNumericValue(posicionActual.charAt(0));
        columnaActual = Character.getNumericValue(posicionActual.charAt(1));
        int filaNueva;
        int columnaNueva;
        String posicionNueva;
        System.out.println("Introduce el NUMERO de la casilla a la que te quieres MOVER");
        do{
            System.out.println("Si prefieres abandonar, escribe " + ANSI_GREEN + "EXIT" + ANSI_WHITE);
            posicionNueva = scanner.nextLine();
            if (posicionNueva.equals("EXIT")){
                System.out.println("El " + ANSI_BLUE + "JUGADOR 2." + ANSI_WHITE + " ha abandonado. EL ganador es el " + ANSI_RED + "JUGADOR 1." + ANSI_WHITE );
                System.exit(0);
            }
        } while (comprobacionMovimientoNegras(posicionActual, posicionNueva));
        filaNueva = Character.getNumericValue(posicionNueva.charAt(0));
        columnaNueva = Character.getNumericValue(posicionNueva.charAt(1));
            if (tablero[filaNueva][columnaNueva] == LIBRE){
                tablero[filaActual][columnaActual] = LIBRE;
                tablero[filaNueva][columnaNueva] = NEGRAS;
            } else if(tablero[filaNueva][columnaNueva] == BLANCAS){
                tablero[filaActual][columnaActual] = LIBRE;
                tablero[filaNueva][columnaNueva] = LIBRE;
                contadorNegras++;
                if (columnaNueva < columnaActual){
                    tablero[filaNueva + 1][columnaNueva -1] = NEGRAS;
                } else if (columnaNueva > columnaActual){
                    tablero[filaNueva + 1][columnaNueva +1] = NEGRAS;
                }
            }      
        comprobarVictoria();
        System.out.println("FIN del turno de NEGRAS");
        System.out.println("Ya se ha  comido " + ANSI_YELLOW + contadorNegras + ANSI_WHITE +  " fichas");
    }

    public boolean comprobacionMovimientoNegras(String posicionActual, String posicionNueva){
        int filaActual;
        int columnaActual;
        int filaNueva;
        int columnaNueva;
        filaNueva = Character.getNumericValue(posicionNueva.charAt(0));
        columnaNueva = Character.getNumericValue(posicionNueva.charAt(1));
        if (!posicionNueva.matches("\\d{2}") || filaNueva > 7 || filaNueva < 0 || columnaNueva < 0 || columnaNueva > 7 ||  Integer.parseInt(posicionNueva) < 00){
            System.out.println(ANSI_RED + "ERROR. Introduce una casilla EXISTENTE."+ ANSI_WHITE);
            return true;
        }
        filaActual = Character.getNumericValue(posicionActual.charAt(0));
        columnaActual = Character.getNumericValue(posicionActual.charAt(1));
        if (filaNueva <= filaActual ){
            System.out.println(ANSI_RED + "ERROR. El movimiento debe AVANZAR"+ ANSI_WHITE);
            return true;
        } else if ( columnaNueva == columnaActual ){
            System.out.println(ANSI_RED + "ERROR. El movimiento debe ser DIAGONAL."+ ANSI_WHITE);
            return true;
        } else if ((filaNueva-filaActual)!=1){
            System.out.println(ANSI_RED + "ERROR. El movimiento solo puede ser de UNA casilla."+ ANSI_WHITE);
            return true;
        } else if (columnaActual - columnaNueva != 1 && columnaActual - columnaNueva != -1){
            System.out.println(ANSI_RED + "ERROR. El movimiento solo puede ser de UNA casilla."+ ANSI_WHITE);
            return true;
        }else if (tablero[filaNueva][columnaNueva] == NEGRAS){
                System.out.println(ANSI_RED + "ERROR. Ya hay una ficha negra ahí."+ ANSI_WHITE);
                return true;
        }
        System.out.println("Movimiento " + ANSI_YELLOW +  "VALIDO" + ANSI_WHITE);
        return false;
    }

    public boolean comprobacionActualNegras(String x) {
        if (!x.matches("\\d{2}") || Integer.parseInt(x) > 77|| Integer.parseInt(x) < 00){
            System.out.println(ANSI_RED + "ERROR. Introduce el numero de una casilla EXISTENTE."+ ANSI_WHITE);
            return true;
        } else {
            int filaComprobar;
            int columnaComprobar;
            filaComprobar = Character.getNumericValue(x.charAt(0));
            columnaComprobar = Character.getNumericValue(x.charAt(1));
            if ( tablero[filaComprobar][columnaComprobar] != NEGRAS){
                System.out.println(ANSI_RED + "ERROR. Elige una posicion donde haya una ficha NEGRA." + ANSI_WHITE);
                return true;
            }
        System.out.println("Seleccion " + ANSI_YELLOW + "VALIDA"+ ANSI_WHITE);
        return false;
        }
    }

    public void comprobarVictoria(){
        boolean hayBlancas = false;
        boolean hayNegras = false;
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                if (tablero[i][j].equals(BLANCAS)){
                    hayBlancas =true;
                } else if (tablero[i][j].equals(NEGRAS)){
                    hayNegras=true;
                }
            }
        }
        if (!hayBlancas){
            System.out.println("Gana el "+ ANSI_BLUE + "JUGADOR 2." + ANSI_WHITE + "Las fichas NEGRAS.");
            System.exit(0);
        }
        if (!hayNegras){
            System.out.println("Gana el " + ANSI_RED + "JUGADOR 1." + ANSI_WHITE + " Las fichas BLANCAS.");
            System.exit(0);
        }
    }

}
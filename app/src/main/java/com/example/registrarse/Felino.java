package com.example.registrarse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Felino extends AppCompatActivity {

    Button boton;
    TextView concepto;
    TextView titulo;


    private int [] mFelino = {R.drawable.ga1, R.drawable.ga2,R.drawable.ga4, R.drawable.ga5,R.drawable.ga6,};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_felino);
    }
    private void cambiarImagen (){
        boton = (Button)findViewById(R.id.boton_cambiar);
        concepto = (TextView) findViewById(R.id.texto_cambiar);
        titulo = (TextView) findViewById(R.id.titulo_cambiar);

        ImageView ivgato = (ImageView)findViewById(R.id.imageView3);
        Random numeros = new Random();
        int pos = numeros.nextInt(5);
        ivgato.setImageResource(mFelino[pos]);
        if ( pos==0){
            titulo.setText(	"CAZA A LA PRESA");
            concepto.setText("¡Los gatos están preparados para cazar! Sus instintos y fisiología están hechos para ayudarles a perseguir, acechar, saltar repentinamente y atrapar a sus presas." +
                    "\n La mejor manera de mantenerlo entretenido es estimulando el sentido de la vista y generando estímulos similares a los que podrían tener en la naturaleza." +
                    "\n\n\tInstrucciones:" +
                    "\n\t\t 1.   Consigue algun palo y cuerda"+
                    "\n\t\t 2.   Consigue algún objeto pequeño para usar de presa (puede ser un pequeño juguete, un objeto muy liviano como una pluma)" +
                    "\n\t\t 3.	Atamos la cuerda a un extremo del palo y en el extremo de la cuerda debemos atar el juguete" +
                    "\n\t\t 4.	Disfruta de un buen rato con él, moviendo la caña y haciendo que la persiga." +
                    "\n\nRecuerda que no ganar puede generar frustración en los gatos, de manera que deja que capture a la presa de vez en cuando para evitar que esto ocurra y no dificultes en exceso el juego.");
        }
        if ( pos==1){
            titulo.setText(	"BUSCA LA PELOTA");
            concepto.setText("Buscar y traer la pelota no es solo un juego de perros, los gatos también disfrutan con estos juguetes." +
                    "\n\n\tInstrucciones:" +
                    "\n\t\t 1.   Encuentra la pelota que más guste a tu compañero o tienes la opción de fabricar una con cuerda de esparto que, además, permitirá a tu gato limar sus uñas con ella."+
                    "\n\t\t 2.	 Lánzala para que vaya en su búsqueda"+
                    "\n\nLanzar una pelota al aire, para tu gato se sentirá mucho como si estuviera cazando una presa que se mueve inesperadamente.");
        }
        if(pos==2){
            titulo.setText(	"ENCUENTRA LA GOLOSINA");
            concepto.setText("¿Acostumbras a desechar los tubos de cartón del papel higiénico? ¡Pues deja de hacerlo! Son perfectos." +
                    "\n\n\tInstrucciones:"+
                    "\n\t\t 1.   Recolectemos tubos de cartón y golosinas y/o premios"+
                    "\n\t\t 2.	 Pegamos varios tubos de cartón (imagen de referencia) de la forma deseada"+
                    "\n\t\t 3.   Disponemos en el interior de algunos tubos golosina"+
                    "\n\nYa verás que tu felino acudirá siguiendo el olor de la comida e intentará sacarla");
        }
        if(pos==3){
            titulo.setText(	"ESCONDITE FELINO");
            concepto.setText("A tu gato le encanta esconderse. Por eso, te proponemos que le ofrezcas escondites a los que no pueda resistirse." +
                    "\n\n\tInstrucciones:"+
                    "\n\t\t 1.   Consigue cajas de cartón." +
                    "\n\t\t 2.   Colócalos por toda la habitación: En lugares altos, debajo de la mesa, detrás de las cortinas" +
                    "\n\t\t 3.   Esconde en las cajas sus juguetes favoritos o algún premio." +
                    "\n\t\t 4.   Acércalo a uno de los escondites y muéstraselo." +
                    "\n\nDespués, solo tendrás que esperar a que merodee alrededor del resto.\n" +
                    " Al día siguiente haz pequeñas modificaciones: Cámbialos de lugar, incluye otros objetos dentro; La idea es que vuelvas a despertar su curiosidad sobre las novedades.");

        }
        if(pos==4){
            titulo.setText(	"BOLSA CON SORPRESAS");
            concepto.setText("A los felinos les encanta esconderse e introducirse en cualquier hueco que vean vacío, Una bolsa de papel puede resultar un juego muy divertido para el animal si sabemos cómo motivarlo. " +
                    "\n\n\tInstrucciones:" +
                    "\n\t\t 1.   Consigue una bolsa de papel y golosinas." +
                    "\n\t\t 2.   Introduce la golosina en la bolsa de papel" +
                    "\n\n, Rápidamente acudirá en su búsqueda y empezará a entretenerse con la bolsa. Y si dejas la bolsa de papel con sorpresa dentro de una caja de cartón; ¡La diversión está más que asegurada!");
        }
    }
    public void Siguiente (View view){
        cambiarImagen();
    }

}
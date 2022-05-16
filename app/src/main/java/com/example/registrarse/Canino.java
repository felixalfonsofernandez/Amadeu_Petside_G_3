package com.example.registrarse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Canino extends AppCompatActivity {
    Button boton;
    TextView concepto;
    TextView titulo;

    private int [] mCanino = {R.drawable.pe1, R.drawable.pe2,R.drawable.pe3, R.drawable.p4, R.drawable.p5 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canino);
    }
    private void cambiarImagen (){
        boton = (Button)findViewById(R.id.boton_cambiar_can);
        concepto = (TextView) findViewById(R.id.texto_cambiar_can);
        titulo = (TextView) findViewById(R.id.titulo_cambiar_can);

        ImageView ivperro = (ImageView)findViewById(R.id.imageView4);
        Random numeros = new Random();
        int pos = numeros.nextInt(5);
        ivperro.setImageResource(mCanino[pos]);

        if ( pos==0){
            titulo.setText(	"ESCONDITE");
            concepto.setText("¿Cuánto tardaría tu perro en encontrarte si te ocultas bien? Jugar al escondite con los canes es un plan divertido tanto dentro de casa, como en el parque o en cualquier lugar donde no sea peligroso." +
                    "\n\n\tInstrucciones:"+
                    "\n\t\t 1.   Mantén quieto a tu compañero"+
                    "\n\t\t 2.	 Busca un lugar y escóndete"+
                    "\n\t\t 3.   Llámalo, así irá en tu búsqueda"+
                    "\n\nY no es sólo divertido, también es una forma de ejercitar el olfato de tu can, su capacidad de rastreo.");
        }
        if ( pos==1){
            titulo.setText(	"CANICROSS");
            concepto.setText("Si te gusta salir a correr esta actividad puede interesarte. \nCanicross consiste en salir a correr con tu perro, " +
                    "\n\n\tInstrucciones:"+
                    "\n\t\t 1.   Atamos a nuestro compañero con un cinturón especial para evitar hacerle o hacerte daño"+
                    "\n\t\t 2.	 Ubicarse al aire libre y realizar la actividad"+
                    "\n\nRecuerda que deben llevar líquido, evitemos la deshidratación del can como la tuya.\nCon esta actividad no solo compartirás tiempo con tu mejor amigo, si no que los dos haréis un gran ejercicio juntos.\n");
        }
                /*"¿Acostumbras a desechar los tubos de cartón del papel higiénico? ¡Pues deja de hacerlo! Son perfectos." +
                    "\n\n\tInstrucciones:"+
                    "\n\t\t 1.   Recolectemos tubos de cartón y golosinas y/o premios"+
                    "\n\t\t 2.	 Pegamos varios tubos de cartón (imagen de referencia) de la forma deseada"+
                    "\n\t\t 3.   Disponemos en el interior de algunos tubos golosina"+
                    "\n\nYa verás que tu felino acudirá siguiendo el olor de la comida e intentará sacarla"*/
        if(pos==2){
            titulo.setText(	"CACERÍA DE GOLOSINAS");
            concepto.setText("Si el día está lluvioso y debes quedarse dentro de casa con tu mascota, nada mejor que jugar a encontrar golosinas." +
                    "\n\n\tInstrucciones:"+
                    "\n\t\t 1.   Asegúrate de que tu mascota se encuentre fuera de tu vista."+
                    "\n\t\t 2.	 Junta algunas de sus golosinas favoritas"+
                    "\n\t\t 3.   Escóndelas en lugares estratégicos"+
                    "\n\nY no es sólo divertido, también es una forma de ejercitar el olfato de tu can, buscando las golosinas.\nRecuerda que puedes guiarlo para evitar la frustración en tu amigo");
        }
        if(pos==3){
            titulo.setText(	"¿ME DEJAS TU JUGUETE?");
            concepto.setText("Este juego permitirá que nuestra mascota se sitúe en estado de alerta y mantenga una óptima condición física"+
                    "\n\n\tInstrucciones:"+
                    "\n\t\t 1.   Conseguir un juguete que resulte atractivo para él y sobre todo cuyo tacto sea suave."+
                    "\n\t\t 2.	 Permitimos que nuestro perro juegue libremente " +
                    "\n\t\t 3.   Quitamos el juguete, obviamente, nuestra mascota no nos dejará " +
                    "\n\t\t 4.	 Empieza un divertido juego de tira y agarra " +
                    "\n\nPodemos incorporarle multitud de movimientos para así permitirle a nuestra mascota un mayor ejercicio físico. Si tienes varios perros no dudes en que esto funcionará.");
        }
        if(pos==4) {
            titulo.setText("JUEGO MUSICAL");
            concepto.setText("Jugar con nuestro perro no únicamente tiene por qué tener la función de estimularlo o excitarlo, sino que también puede ser una excelente manera de relajarlo.\n" +
                    "\nEl refrán popular nos dice que la música amansa a las fieras, y tiene gran razón, de hecho, los efectos de la musicoterapia son diversos, positivos y sobradamente demostrados." +
                    "\n\n\tInstrucciones:" +
                    "\n\t\t 1.   Busca una superficie donde tu perro pueda tumbarse cómodamente y relajarse" +
                    "\n\t\t 2.	 Permanece a su lado, a medida que se vaya calmando podrás colmarlo de afecto" +
                    "\n\t\t 3.   Ponle música" +
                    "\n\nA los perros les puede encantar la música que incorpore aullidos de lobos u otros sonidos de animales salvajes, esto estimulará su cerebro a la vez que lo mantiene relajado");
        }
    }

    public void Siguiente (View view){
        cambiarImagen();
    }
}
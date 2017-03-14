/*
 
Obtiene un numero aleatorio y lo muestra en monitor serie
*/
 
//Variable donde almacenaremos el numero aleatorio

#include "WProgram.h"
void setup();
void loop();
long randomNumber;
long randomNumber2;
long randomNumber3;
long randomNumber4;
//Funci\u00f3n de inicializaci\u00f3n
void setup() {
  
  //Inicializamos la comunicaci\u00f3n serial
  Serial.begin(9600);
  
  //Escribimos por el puerto serie mensaje de inicio
  Serial.println("Inicio de sketch - secuencia de numeros aleatorios");
      
}
 
//Bucle principal
void loop() {
 
  
  //Genera un numero aleatorio entre 1 y 100
  randomNumber = random(50,150);
  randomNumber2 = random(50,150);
  randomNumber4 = random(50,150);
  randomNumber3 = random(1,10);

  
  //Escribimos el numero aleatorio por el puerto serie

  Serial.print(randomNumber);
  Serial.print("@");
  Serial.print(randomNumber2);
  Serial.print("@");
  Serial.print(randomNumber4);
  Serial.print("@");
  Serial.print(randomNumber3);
  Serial.println(" ");

  
  delay(2000);
}


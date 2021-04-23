#include "BluetoothSerial.h"
#include <FastLED.h>

// How many leds in your strip?
#define NUM_LEDS 16
#define DATA_PIN 12
#define BLINK_SPEED 50    //Higher the value lower the speed

#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

// Define the array of leds
CRGB leds[NUM_LEDS];

BluetoothSerial SerialBT;
String str = "BikeNavi Directions not started";
String str2 = "BikeNavi Directions not started";
int n;
bool currconnectionstatus=false;
bool prevconnectionstatus=false;
int counter=0;

void setup() {
  //BT Classic code
  Serial.begin(115200);
  SerialBT.begin("BikeNaviBT"); //Bluetooth device name
  Serial.println("The device started, now you can pair it with bluetooth!");
  //Ringlight code
  FastLED.addLeds<NEOPIXEL, DATA_PIN>(leds, NUM_LEDS);  // GRB ordering is assumed
}

void loop() {
  //BTClassic code
  if (Serial.available()) {
    SerialBT.write(Serial.read());
  }
  if (SerialBT.available()) {
    str = SerialBT.readStringUntil('\n');       //reads until \n
    str2 = str.substring(0, str.length() - 1);   //removes \n
    Serial.println(str);
    Serial.println(str.length());
    counter=0;
  }

  //if BT is connected to any device
   if (SerialBT.hasClient()) {
      currconnectionstatus=true;
    }
    else{
      currconnectionstatus=false;
    }

    if(currconnectionstatus==false)
    {
      leds[random(0,15)] = CRGB::Blue;   //RGB by color
      FastLED.show();       //updtes lights
    }
  //Connected - One time
  if (currconnectionstatus==true&&prevconnectionstatus==false)
  {
    Serial.println("connected");
    for (int i = 0; i < 16; i++)
    {
      // Turn the LED on, then pause
      leds[i] = CRGB::Blue;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
    }
    for (int i = 0; i < 16; i++)
    {
      leds[i] = CRGB::Black;    //RGB by color
      FastLED.show();       //updtes lights
    }
    delay(BLINK_SPEED);
    for (int j = 0; j < 3; j++)
    {
      for (int i = 0; i < 16; i++)
      {
        // Turn the LED on, then pause
        leds[i] = CRGB::Blue;    //RGB by color
        FastLED.show();       //updtes lights
      }
      delay(50);
      for (int i = 0; i < 16; i++)
      {
        leds[i] = CRGB::Black;    //RGB by color
        FastLED.show();       //updtes lights
      }
      delay(BLINK_SPEED);
    }
    str = "Direction not defined";
  }

  //Disconnected - One time
  else if (currconnectionstatus==false&&prevconnectionstatus==true)
  {
    Serial.println("disconnected");
    for (int i = 0; i < 16; i++)
    {
      // Turn the LED on, then pause
      leds[i] = 0xC0C0C0;;   //Hex - Silver
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
    }
    for (int i = 0; i < 16; i++)
    {
      leds[i] = 0xC0C0C0;;   //Hex - Silver
      FastLED.show();       //updtes lights
    }
    delay(BLINK_SPEED);
    for (int j = 0; j < 3; j++)
    {
      for (int i = 0; i < 16; i++)
      {
        // Turn the LED on, then pause
        leds[i] = 0xC0C0C0;;   //Hex - Silver
        FastLED.show();       //updtes lights
      }
      delay(BLINK_SPEED);
      for (int i = 0; i < 16; i++)
      {
        leds[i] = CRGB::Black;    //RGB by color
        FastLED.show();       //updtes lights
      }
      delay(BLINK_SPEED);
    }
    str = "Direction not defined";
  }

  //Straight - continious
  if (str == "straight"||counter>=8||str2 == "straight")
  {
    Serial.println("straight");
    for (int i = 0; i < 3; i++)
    {
      leds[0] = CRGB::Green;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED*2);
      leds[15] = CRGB::Green;   //RGB by color
      leds[1] = CRGB::Green;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED*2);
      leds[14] = CRGB::Green;   //RGB by color
      leds[2] = CRGB::Green;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED*2);
    }
  }

  //Slight right - continious
  else if (str == "slight right"||str2 == "slight right")
  {
    Serial.println("slight right");
    for (int i = 0; i < 3; i++)
    {
      leds[2] = CRGB::Green;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED*2);
      leds[3] = CRGB::Green;   //RGB by color
      leds[1] = CRGB::Green;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED*2);
      leds[4] = CRGB::Green;   //RGB by color
      leds[0] = CRGB::Green;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED*2);
    }
    counter++;
  }

  //Right - continious
  else if (str == "right"||str2 == "right")
  {
    //Serial.println("right");
    for (int i = 0; i < 3; i++)
    {
      leds[4] = CRGB::Yellow;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
      leds[5] = CRGB::Yellow;   //RGB by color
      leds[3] = CRGB::Yellow;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
      leds[2] = CRGB::Yellow;   //RGB by color
      leds[6] = CRGB::Yellow;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
    }
    counter++;
  }

  //Sharp right - continious
  else if (str == "sharp right"||str2 == "sharp right")
  {
    //Serial.println("sharp right");
    for (int i = 0; i < 3; i++)
    {
      leds[4] = CRGB::Red;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
      leds[5] = CRGB::Red;   //RGB by color
      leds[3] = CRGB::Red;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
      leds[2] = CRGB::Red;   //RGB by color
      leds[6] = CRGB::Red;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
    }
    counter++;
  }

  //Slight left - continious
  else if (str == "slight left"||str2 == "slight left")
  {
    Serial.println("slight left");
    for (int i = 0; i < 3; i++)
    {
      leds[14] = CRGB::Green;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
      leds[15] = CRGB::Green;   //RGB by color
      leds[13] = CRGB::Green;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
      leds[0] = CRGB::Green;   //RGB by color
      leds[12] = CRGB::Green;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
    }
    counter++;
  }

  //Left - continious
  else if (str == "left"||str2 == "left")
  {
    Serial.println("left");
    for (int i = 0; i < 3; i++)
    {
      leds[12] = CRGB::Yellow;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
      leds[13] = CRGB::Yellow;   //RGB by color
      leds[11] = CRGB::Yellow;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
      leds[14] = CRGB::Yellow;   //RGB by color
      leds[10] = CRGB::Yellow;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
    }
    counter++;
  }

  //Sharp left - continious
  else if (str == "sharp left"||str2 == "sharp left")
  {
    Serial.println("sharp left");
    for (int i = 0; i < 3; i++)
    {
      leds[12] = CRGB::Red;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
      leds[13] = CRGB::Red;   //RGB by color
      leds[11] = CRGB::Red;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
      leds[14] = CRGB::Red;   //RGB by color
      leds[10] = CRGB::Red;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
    }
    counter++;
  }

  //Uturn - continious
  else if (str == "uturn"||str2 == "uturn")
  {
    Serial.println("uturn");
    for (int i = 0; i < 3; i++)
    {
      leds[8] = CRGB::Red;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
      leds[9] = CRGB::Red;   //RGB by color
      leds[7] = CRGB::Red;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
      leds[10] = CRGB::Red;   //RGB by color
      leds[6] = CRGB::Red;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
      leds[11] = CRGB::Red;   //RGB by color
      leds[5] = CRGB::Red;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
      leds[12] = CRGB::Red;   //RGB by color
      leds[4] = CRGB::Red;   //RGB by color
      FastLED.show();       //updtes lights
      delay(BLINK_SPEED);
    }
    counter++;
  }

  delay(BLINK_SPEED);
  for (int i = 0; i < 16; i++)
  {
    leds[i] = CRGB::Black;    //RGB by color
    FastLED.show();       //updtes lights
  }
  prevconnectionstatus=currconnectionstatus;
}





/*
    Code for reference

    //Ringlight code
  for(int i=0;i<16;i++)
  {
   // Turn the LED on, then pause
   leds[i] = 0xFFFF00;   //RGB by hex
   FastLED.show();       //updtes lights
   delay(500);

   // Now turn the LED off, then pause
   leds[i] = CRGB::Black;    //RGB by color
   FastLED.show();
   delay(500);
  }*/

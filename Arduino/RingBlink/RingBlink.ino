#include <FastLED.h>

// How many leds in your strip?
// For led chips like WS2812, which have a data line, ground, and power, you just
// need to define DATA_PIN.  For led chipsets that are SPI based (four wires - data, clock,
// ground, and power), like the LPD8806 define both DATA_PIN and CLOCK_PIN
// Clock pin only needed for SPI based chipsets when not using hardware SPI
#define DATA_PIN 12
#define CLOCK_PIN 13
#define NUM_LEDS 16

// Define the array of leds
CRGB leds[NUM_LEDS];

void setup() { 
    FastLED.addLeds<NEOPIXEL, DATA_PIN>(leds, NUM_LEDS);  // GRB ordering is assumed  
}

void loop() { 
  // Turn the LED on, then pause
  for(int i=0;i<16;i++)
  {
    leds[i] = 0xFFFF00;   //RGB hex
    FastLED.show();
    delay(500);
    
    // Now turn the LED off, then pause
    leds[i] = CRGB::Black;
    FastLED.show();
    delay(500);
  }
}

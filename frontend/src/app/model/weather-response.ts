import {WeatherLocation} from './weather-location';
import {WeatherCurrent} from './weather-current';
import {WeatherForecast} from './weather-forecast';

export interface WeatherResponse {
  name: string;
  country: string;
  localtime: string;
  tempC: number;
  windKph: number;
  airQuality?: { [key: string]: any };
}

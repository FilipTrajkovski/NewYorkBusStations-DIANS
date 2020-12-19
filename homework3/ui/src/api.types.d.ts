/* tslint:disable */
/* eslint-disable */

interface BaseDto {
    id: number,
    name: string
}

export interface BusDto extends BaseDto {}

export interface BusStationDto extends BaseDto {
    longitude: string,
    latitude: string
}

export interface BusConnectionDto {
    bus: BusDto,
    busStations: Array<BusStationDto>
}

export interface BusStationConnectionDto {
    busStation: BusStationDto,
    buses: Array<BusDto>
}

export interface DropdownOption {
    key: number|string,
    text: string,
    value: number|string
}

export interface SearchResponse {
    buses: Array<BusDto>,
    busStations: Array<BusStationDto>
}
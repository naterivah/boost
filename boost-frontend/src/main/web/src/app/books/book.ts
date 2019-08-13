import {Auditable} from "../common/auditable";

export class Book extends Auditable{
  public id:number;
  public title:string;
  public description:string;
  public totalDuration:string;
}
export class BookDto {
  public title:string;
  public description:string;
  public totalDuration:string;
  public category:string;
  public  cover:any;
  public fileName:string;
  public contentType: string;
}

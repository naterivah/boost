import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../login/authenticationservice';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {environment} from "../../environments/environment";
import {Slugify} from "../common/slugify";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  public loading:boolean;
  public books:any;

  constructor(private http: HttpClient, private router: Router, private authenticationService: AuthenticationService) {
  }

  isLoggedIn() {
    return this.getUser() !== null;
  }

  getUser() {
    return this.authenticationService.getUser();
  }

  getBookDetailLink(book){
    let link = '/books/' + Slugify.slugify(book.title) + '/' + book.id + '/' + 'view';
    return link;
  }
  ngOnInit() {
      this.loading = true;
      this.books=null;
      this.http.get<any[]>(environment.backendUrl +'/book/last', {}).subscribe(
        (datas) => {
          this.books = datas;
            this.loading=false;
        },
        (err) => {
          console.log(err);
        },
        () => {
        },
      );
  }

}

import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import {ErrorComponent} from './error/error.component';
import {AuthGuardService} from './login/authguardservice';
import {HomeComponent} from './home/home.component';
import {BooksComponent} from "./books/books.component";
import {BooksDetailComponent} from "./books-detail/books-detail.component";

const routes: Route[] = [
  {path: '', component: HomeComponent},
  {path: 'error', component: ErrorComponent},
  {path: 'books', component: BooksComponent, canActivate: [AuthGuardService], data: {expectedRole: ['USER','ANONYMOUS', 'ADMIN']}},
  {path: 'books/:title/:id/:editMode', component: BooksDetailComponent, canActivate: [AuthGuardService], data: {expectedRole: ['USER','ANONYMOUS', 'ADMIN']}},
  {path: '**', component: ErrorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
import {Component, OnDestroy, OnInit} from '@angular/core';
import {AppService} from '../app.service';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';
import {AppComponent} from '../app.component';


class User {
  id: string;
  name: string;
  img: string;
  email: string;
  telephone: string;
  lastName = ' ';
  firstName = ' ';
  middleName = ' ';
}


class Img {
  img: string;
  name: string;
}

class Massages {
  user: User;
  imgs: Img[];
  text = ' ';

}

class Orders {
  name: string;
  done: boolean;
  executed: string;
  crate: string;
  level: string;
  creator: User = new User();
  executor: User[] = [];
  text = ' ';
  imgs: Img[] = [];
  id = ' ';
  massages: Massages[] = [];
}

@Component({
  selector: 'app-list-order',
  templateUrl: './list-order.component.html',
  styleUrls: ['./list-order.component.css']
})
export class ListOrderComponent implements OnInit, OnDestroy {
  orders: Orders[] = [];
  upload;

  constructor(private app: AppService, private http: HttpClient, private router: Router,
              private cookieService: CookieService, public appc: AppComponent) {
    this.appc.cont = true;
    this.upload = false;
    this.uploadOrder();
  }

  uploadOrder(): any {
    if (this.upload == null) {
      return;
    }
    if (!this.upload) {
      this.http.get(this.app.serverURL + 'task/tasks').subscribe((response: Orders[]) => {

        this.orders = response;

      });
    } else {
      this.http.post(this.app.serverURL + 'task/tasks', null).subscribe((response: Orders[]) => {

        this.orders = response;

      });
    }
  }


  ngOnInit(): void {
  }

  text(id: string): any {
    this.router.navigateByUrl('/order/' + id);
  }

  ngOnDestroy(): void {
    this.upload = null;
  }
}

import {Component, OnDestroy, OnInit} from '@angular/core';
import {AppService} from '../app.service';
import {AppComponent} from '../app.component';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';

class Role {
  authority: string;
}

class Orders {

  creator: Login = new Login();
  name: string;
  id: string;
}

class Img {
  img = '';
}

class Notifications {

  id: string;
  text: string;
  level: string;
  taskId: Orders;
  data: string;
  massages: Massages;
  notificationType: string;

}

class Massages {
  user: Login;
  imgs: Img[];
  text = ' ';


}

class Login {
  id = '';
  username = ' ';
  img: Img = new Img();
  imgFon: Img = new Img();
  authorities: Role[] = [];
  password: string;
  confirmPassword: string;
  email: string;
  firstName: string;
  lastName: string;
  middleName: string;
  telephone: string;
  accountNonExpired: string;
  accountNonLocked: string;
  enabled: string;
  notificationsEnabled = true;
  notifications: Notifications[];
  credentialsNonExpired: string;
  emailConfirmed: any;
  isEnabled: any;
}

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit, OnDestroy {

  data = false;
  id: string;
  login = new Login();
  uploader;

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute, public app: AppService, public appc: AppComponent) {
    this.route.params.subscribe((params: any) => this.id = params.id);
    this.route.queryParams.subscribe(params => {
      this.data = params['data'];
    });

    this.uploader = false;
    this.uplod();
    this.appc.cont = false;

  }

  uplod(): any {

    if (this.uploader == null) {

      return;
    }
    if (!this.uploader) {

      this.http.get(this.app.serverURL + 'user/' + this.id).subscribe((next: Login) => {
        this.login = next;
        console.log(next);
        this.opNot(500);
        this.uploader = true;
        this.uplod();
      }, error => this.uplod());

    } else {
      this.opNot(4000);

    }

  }

  opNot(s: number) {
    this.http.get(this.app.serverURL + 'notifications/get/' + s).subscribe((next: Notifications[]) => {
      this.login.notifications = next;
      this.uplod();
      console.log(next);
      console.log(this.app.serverURL + 'notifications/get/' + s);
    }, error => this.uplod());
  }


  ngOnInit(): void {
    this.route.paramMap.subscribe((params: ParamMap) => {

    });
  }


  bolConvertor(s: any) {
    if (s == true) {
      return 'да';
    } else if (s == false) {
      return 'нет';
    } else {
      return s;
    }
  }

  close(id: string) {
    this.http.delete(this.app.serverURL + 'notifications/' + id).subscribe((next: Notifications[]) => {
      this.login.notifications = next;
    });
  }

  ngOnDestroy(): void {
    this.uploader = null;
  }

  mass(item: Notifications, massages: string) {
    return item.notificationType == massages;
  }

  notificationsED() {
    console.log(1);
    let b = 0;
    if (this.login.notificationsEnabled) {
      b = 1;
    } else {
      b = 0;
    }
    this.http.put(this.app.serverURL + 'notificationsed/', b).subscribe((next: Login) => {
      this.login = next;
    });
  }
}

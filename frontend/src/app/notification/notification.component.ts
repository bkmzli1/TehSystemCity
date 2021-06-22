import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {AppService} from '../app.service';
import {AppComponent} from '../app.component';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit {


  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute, public app: AppService, public appc: AppComponent) {
    app.isEmail();
  }

  ngOnInit(): void {
  }

  enableEmail() {
    this.http.get(this.app.serverURL + 'emilactive/').subscribe((next) => {

    });
  }

  notifyEmail() {
    return !this.app.notif && this.app.authenticated;
  }
}

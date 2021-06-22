import {Component, OnInit} from '@angular/core';
import {AppService} from '../app.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';
import {flatMap} from 'rxjs/internal/operators';
import {AppComponent} from '../app.component';

class User {
  id: string;
  name: string;
  img: Img;
  email: string;
  telephone: string;
  lastName = ' ';
  firstName = ' ';
  middleName = ' ';
}


class Img {
  img: string;
}

class Project {
  id: string;
  name: string;
  userCrate: User;
  userExecutor: User[];
  text: string;
  img = {};
}

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']
})
export class CreateOrderComponent implements OnInit {
  ex: User[];
  exList: User[] = [];
  error: string[] = [];

  user: User;
  selectedValue;
  text = '';
  fileToUpload: FormData = null;
  types = 'INFO';
  name: string;
  maxText = 255;
  successful: string[] = [];
  serchUser: User[] = [];


  constructor(private app: AppService, private http: HttpClient, private router: Router,
              private cookieService: CookieService, public appc: AppComponent) {

    this.appc.cont = true;
    this.http.get(app.serverURL + 'executor').subscribe((response: User[]) => {
      this.ex = response;
    });
    this.http.get(app.serverURL + 'userRole/EXECUTOR').subscribe((response: User[]) => {
      this.serchUser = response;
      console.log(response);
      console.log(1);
    });

  }

  test(item: string): any {
    this.http.get(this.app.serverURL + 'user/' + item).subscribe((response: User) => {
      this.user = response;

    });
  }

  ngOnInit(): void {
    let inputs = document.querySelectorAll('.input__file');
    Array.prototype.forEach.call(inputs, function (input) {
      let label = input.nextElementSibling,
        labelVal = label.querySelector('.input__file-button-text').innerText;

      input.addEventListener('change', function (e) {
        let countFiles = '';
        if (this.files && this.files.length >= 1)
          countFiles = this.files.length;

        if (countFiles)
          label.querySelector('.input__file-button-text').innerText = 'Выбрано файлов: ' + countFiles;
        else
          label.querySelector('.input__file-button-text').innerText = labelVal;
      });
    });
  }

  addEx(): any {
    let isUser = true;
    let itemUser;

    for (itemUser of this.exList) {

      if (itemUser == this.user) {
        isUser = false;
      }
    }
    if (isUser) {
      if (this.user !== undefined) {
        this.exList.push(this.user);
        this.error = [];

      } else {

        this.error = ['Не выбран пользователь'];
      }

    }

  }

  handleFileInput(fileList: FileList) {

    const uploadData = new FormData();
    for (let i = 0; i < fileList.length; i++) {
      uploadData.append('mfImg', fileList[i], fileList.item(i).name);
    }
    this.fileToUpload = uploadData;

  }

  type(numbe: any) {

    this.types = numbe;
  }

  create() {


    if (this.exList.length >= 1) {

      this.error = [];
      const headers = new HttpHeaders();
      headers.set('Content-Type', 'multipart/form-data');
      headers.set('Accept', 'application/json');
      const list = this.listId(this.exList);
      this.http.post(this.app.serverURL + 'img/' + 'TASK', this.fileToUpload, {headers}).subscribe(
        (next2: string[]) => {
          const taskCreate = {

            level: this.types,
            name: this.name,
            text: this.text,
            executor: list,
            imgs: next2
          };

          this.http.post(this.app.serverURL + 'task/create', taskCreate).subscribe((next: any) => {

            if (next.error !== undefined) {
              this.error = next.error;
            } else {
              this.router.navigateByUrl('/order/' + next.id);
            }
          });
        },
        error2 => console.log(error2)
      );

    } else {

      this.error = ['Не выбран пользователь'];
    }
  }

  listId(users: User[]): any {
    const userId = [];
    let item;
    for (item of users) {
      userId.push(item.id);
    }
    return userId;
  }


  a(id: string) {
    console.log(id);
  }

  close(user: User) {
    this.exList.forEach( (item, index) => {
      if(item === user) this.exList.splice(index,1);
    });
  }
}


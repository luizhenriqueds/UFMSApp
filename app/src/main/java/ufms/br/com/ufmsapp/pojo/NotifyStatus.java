/*
 * Copyright [2016] [UFMS]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ufms.br.com.ufmsapp.pojo;


public class NotifyStatus {

    int notifyId;
    int notifyStatus;
    int eventoKey;


    public NotifyStatus(int notifyStatus, int eventoKey) {
        this.eventoKey = eventoKey;
        this.notifyStatus = notifyStatus;
    }

    public NotifyStatus(int notifyId, int notifyStatus, int eventoKey) {
        this.notifyId = notifyId;
        this.notifyStatus = notifyStatus;
        this.eventoKey = eventoKey;
    }

    public int getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(int notifyId) {
        this.notifyId = notifyId;
    }

    public int getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(int notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public int getEventoKey() {
        return eventoKey;
    }

    public void setEventoKey(int eventoKey) {
        this.eventoKey = eventoKey;
    }

    @Override
    public String toString() {
        return "NotifyStatus{" +
                "notifyId=" + notifyId +
                ", notifyStatus=" + notifyStatus +
                ", eventoKey=" + eventoKey +
                '}';
    }
}

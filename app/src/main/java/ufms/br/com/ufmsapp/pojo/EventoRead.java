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


public class EventoRead {

    int eventoReadId;
    int eventoReadStatus;
    int eventoKey;

    public EventoRead(int eventoReadStatus, int eventoKey) {
        this.eventoReadStatus = eventoReadStatus;
        this.eventoKey = eventoKey;
    }

    public EventoRead(int eventoReadId, int eventoReadStatus, int eventoKey) {
        this.eventoReadId = eventoReadId;
        this.eventoReadStatus = eventoReadStatus;
        this.eventoKey = eventoKey;
    }

    public int getEventoReadId() {
        return eventoReadId;
    }

    public void setEventoReadId(int eventoReadId) {
        this.eventoReadId = eventoReadId;
    }

    public int getEventoReadStatus() {
        return eventoReadStatus;
    }

    public void setEventoReadStatus(int eventoReadStatus) {
        this.eventoReadStatus = eventoReadStatus;
    }

    public int getEventoKey() {
        return eventoKey;
    }

    public void setEventoKey(int eventoKey) {
        this.eventoKey = eventoKey;
    }

    @Override
    public String toString() {
        return "EventoRead{" +
                "eventoReadId=" + eventoReadId +
                ", eventoReadStatus=" + eventoReadStatus +
                ", eventoKey=" + eventoKey +
                '}';
    }
}

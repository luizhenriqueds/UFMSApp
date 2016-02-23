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


public class EventoFavorite {

    int eventoFavoriteId;
    int eventoFavoriteStatus;
    int eventoKey;

    public EventoFavorite(int eventoFavoriteStatus, int eventoKey) {
        this.eventoFavoriteStatus = eventoFavoriteStatus;
        this.eventoKey = eventoKey;
    }

    public EventoFavorite(int eventoFavoriteId, int eventoFavoriteStatus, int eventoKey) {
        this.eventoFavoriteId = eventoFavoriteId;
        this.eventoFavoriteStatus = eventoFavoriteStatus;
        this.eventoKey = eventoKey;
    }

    public int getEventoFavoriteId() {
        return eventoFavoriteId;
    }

    public void setEventoFavoriteId(int eventoFavoriteId) {
        this.eventoFavoriteId = eventoFavoriteId;
    }

    public int getEventoFavoriteStatus() {
        return eventoFavoriteStatus;
    }

    public void setEventoFavoriteStatus(int eventoFavoriteStatus) {
        this.eventoFavoriteStatus = eventoFavoriteStatus;
    }

    public int getEventoKey() {
        return eventoKey;
    }

    public void setEventoKey(int eventoKey) {
        this.eventoKey = eventoKey;
    }

    @Override
    public String toString() {
        return "EventoFavorite{" +
                "eventoFavoriteId=" + eventoFavoriteId +
                ", eventoFavoriteStatus=" + eventoFavoriteStatus +
                ", eventoKey=" + eventoKey +
                '}';
    }
}

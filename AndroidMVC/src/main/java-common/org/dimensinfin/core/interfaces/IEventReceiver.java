package org.dimensinfin.core.interfaces;

import org.dimensinfin.core.domain.IntercommunicationEvent;

public interface IEventReceiver {
    void receiveEvent(IntercommunicationEvent event);
}

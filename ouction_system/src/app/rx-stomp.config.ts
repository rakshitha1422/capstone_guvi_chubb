import { InjectableRxStompConfig } from '@stomp/ng2-stompjs';

export const rxStompConfig: InjectableRxStompConfig = {
    brokerURL: 'ws://localhost:8080/ws',

    // Optional heartbeat configuration
    heartbeatIncoming: 4000, // Milliseconds
    heartbeatOutgoing: 4000, // Milliseconds

    reconnectDelay: 5000, // Auto reconnect in milliseconds

    debug: (msg: string): void => {
        console.log(new Date(), msg);
    },
};

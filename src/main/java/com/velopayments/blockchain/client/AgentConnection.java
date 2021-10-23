package com.velopayments.blockchain.client;

import com.velopayments.blockchain.cert.Certificate;
import com.velopayments.blockchain.init.Initializer;
import com.velopayments.blockchain.util.Entity;
import com.velopayments.blockchain.util.PrivateEntity;
import java.io.IOException;
import java.util.UUID;

public class AgentConnection implements Connection {

    private SocketAdapter sock;
    private Entity agentEntity;
    private PrivateEntity clientEntity;
    private long clientPacketId;
    private long serverPacketId;

    /**
     * The AgentConnection private constructor takes a SocketAdapter, the Entity
     * for the blockchain agent, and the PrivateEntity for the client.
     *
     * @param sock          The SocketAdapter for this connection.
     * @param agentEntity   The public Entity for the agent.
     * @param clientEntity  The PrivateEntity for the client.
     */
    private AgentConnection(
            SocketAdapter sock, Entity agentEntity, PrivateEntity clientEntity)
            throws IOException {

        this.sock = sock;
        this.agentEntity = agentEntity;
        this.clientEntity = clientEntity;

        connectNative(Initializer.getInstance());
    }

    /**
     * Reset the client connection.
     */
    public void reset() throws IOException {

        resetNative(Initializer.getInstance());
    }

    /**
     * Send a transaction submission request.
     *
     * @param transaction   The transaction to submit.
     * @param requestOffset A unique request offset used to link the response.
     */
    public void sendTransactionSubmissionRequest(
            Certificate transaction, long requestOffset)
            throws IOException {

        sendTransactionSubmissionRequestNative(
            Initializer.getInstance(), transaction, requestOffset);
    }

    /**
     * Send a get latest block id request.
     *
     * @param requestOffset A unique request offset used to link the response.
     */
    public void sendGetLatestBlockIdRequest(
            long requestOffset)
            throws IOException {

        sendGetLatestBlockIdRequestNative(
            Initializer.getInstance(), requestOffset);
    }

    /**
     * Get a block by id.
     *
     * @param blockId       The block id.
     * @param requestOffset A unique request offset used to link the response.
     */
    public void sendGetBlockByIdRequest(
            UUID blockId, long requestOffset)
            throws IOException {

        sendGetBlockByIdRequestNative(
            Initializer.getInstance(), blockId, requestOffset);
    }

    /**
     * Get a block id by height.
     *
     * @param height        The block height.
     * @param requestOffset A unique request offset used to link the response.
     */
    public void sendGetBlockIdByHeightRequest(
            long height, long requestOffset)
            throws IOException {

        sendGetBlockIdByHeightRequestNative(
            Initializer.getInstance(), height, requestOffset);
    }

    /**
     * Get a transaction by id.
     *
     * @param txnId         The transaction id.
     * @param requestOffset A unique request offset used to link the response.
     */
    public void sendGetTransactionByIdRequest(
            UUID txnId, long requestOffset)
            throws IOException {

        sendGetTransactionByIdRequestNative(
            Initializer.getInstance(), txnId, requestOffset);
    }

    /**
     * Get an artifact by id.
     *
     * @param artifactId    The artifact id.
     * @param requestOffset A unique request offset used to link the response.
     */
    public void sendGetArtifactByIdRequest(
            UUID artifactId, long requestOffset)
            throws IOException {

        sendGetArtifactByIdRequestNative(
            Initializer.getInstance(), artifactId, requestOffset);
    }

    /**
     * Send a connection status request.
     *
     * @param requestOffset A unique request offset used to link the response.
     */
    public void sendConnectionStatusRequest(
            long requestOffset)
            throws IOException {

        sendConnectionStatusRequestNative(
            Initializer.getInstance(), requestOffset);
    }

    /**
     * Read a response from the API.
     *
     * @return a response object.
     */
    public Response readResponse()
            throws IOException {

        return readResponseNative(Initializer.getInstance());
    }

    private native void connectNative(long nativeInst) throws IOException;
    private native void resetNative(long nativeInst) throws IOException;
    private native  void sendTransactionSubmissionRequestNative(
            long nativeInst, Certificate transaction, long requestOffset)
            throws IOException;
    private native void sendGetLatestBlockIdRequestNative(
            long nativeInst, long requestOffset)
            throws IOException;
    private native void sendGetBlockByIdRequestNative(
            long nativeInst, UUID blockId, long requestOffset)
            throws IOException;
    private native void sendGetBlockIdByHeightRequestNative(
            long nativeInst, long height, long requestOffset)
            throws IOException;
    private native void sendGetTransactionByIdRequestNative(
            long nativeInst, UUID txnId, long requestOffset)
            throws IOException;
    private native void sendGetArtifactByIdRequestNative(
            long nativeInst, UUID artifactId, long requestOffset)
            throws IOException;
    private native void sendConnectionStatusRequestNative(
            long nativeInst, long requestOffset)
            throws IOException;
    private native Response readResponseNative(long nativeInst)
            throws IOException;
}

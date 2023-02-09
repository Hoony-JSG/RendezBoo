

import '../Styles/Docking3ing.css'

const Docking3ing = () => {


    return (
        <div style={{ 
            display:'flex',
            flexDirection: 'row',
            alignItems: 'center',
            justifyContent: 'space-between',
            overflowX: 'scroll',
            padding: '20px',
            }}>
            <div id="main">
                <div id="cam-group">
                    <div id="cam"></div>
                    <div id="cam"></div>
                    <div id="cam"></div>
                    <div id="cam"></div>
                    <div id="cam"></div>
                    <div id="cam"></div>
                </div>
                <div id="chat">

                </div>
            </div>
            <div id="side">
                <div id="game">

                </div>
                <div id="btn-group">

                </div>
            </div>
        </div>
    )
}

export default Docking3ing